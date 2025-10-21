import {Component, inject, OnInit} from '@angular/core';
import {MenuItem, MessageService} from 'primeng/api';
import {AppUseMotives, ClientDTO} from '../integration/domain/client-dto';
import {ClientService} from '../integration/services/client/client.service';
import {Router} from '@angular/router';
import {
  MeteorologicalStationService
} from '../integration/services/meteorological-station/meteorological-station.service';
import {MeteorologicalStationDTO} from '../integration/domain/meteorological-station-dto';
import {ForecastService} from '../integration/services/forecast/forecast.service';
import {ForecastDTO} from '../integration/domain/forecast-dto';
import {LoadingSpinnerStore} from '../loading/store/loading-spinner.store';

@Component({
  selector: 'app-home',
  standalone: false,
  templateUrl: './home.component.html',
  styleUrl: './home.component.css',
  providers: [MessageService]
})
export class HomeComponent implements OnInit {

  items: MenuItem[] = [];

  options: google.maps.MapOptions = {
    mapId: "StationsMap",
    center: {lat: 46.2363207, lng: 24.8464017},
    zoom: 7,
    tiltInteractionEnabled: false,
    streetViewControl: false,
    fullscreenControl: false,
    disableDefaultUI: true
  };

  private messageService: MessageService = inject(MessageService);

  private loadingSpinnerStore: LoadingSpinnerStore = inject(LoadingSpinnerStore);

  private meteoStationService: MeteorologicalStationService = inject(MeteorologicalStationService);

  private router: Router = inject(Router);

  selectedStation: MeteorologicalStationDTO;

  stations: MeteorologicalStationDTO[] = [];

  client: ClientDTO;

  private clientService: ClientService = inject(ClientService);

  protected forecast: ForecastDTO;

  private forecastService: ForecastService = inject(ForecastService);

  constructor() {
    this.selectedStation = {
      id: "",
      name: "",
      code: "",
      latitude: 0,
      longitude: 0
    }

    this.forecast = {
      id: "",
      meteorologicalStation: this.selectedStation,
      date: new Date(),
      cloudiness: 0,
      dewPoint: 0,
      pressure: 0,
      humidity: 0,
      temperature: 0,
      windDirection: 0,
      windSpeed: 0,
      review: null,
      metar: ""
    }

    this.client = {
      id: "",
      username: "",
      profilePhoto: 1,
      email: "",
      motive: AppUseMotives.AppUseMotivesType.FORECAST_FOR_FLYING
    }
  }

  async ngOnInit() {
    this.stations = await this.meteoStationService.getAll();
  }

  clearStation() {
    this.selectedStation = {
      id: "",
      name: "",
      code: "",
      latitude: 0,
      longitude: 0
    }
  }

  async generatePrediction() {
    try {
      this.loadingSpinnerStore.update({loading: true})

      let id = localStorage.getItem("client-id") ?? ""
      this.client = await this.clientService.getById(id);

      this.forecast = await this.forecastService.generate(this.client.username, this.selectedStation.name)

      await this.router.navigate([`/history/${this.forecast.id}`]);
    } catch (e) {
      this.messageService.add({
        severity: "error",
        summary: "Prediction failed",
        detail: "The station you chose could not be found!",
      });
    } finally {
      this.loadingSpinnerStore.update({loading: false})
    }
  }

  selectStation(station: MeteorologicalStationDTO) {
    this.selectedStation = station;
  }
}
