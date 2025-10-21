import {Component, inject} from '@angular/core';
import {ActivatedRoute, Params} from '@angular/router';
import {ForecastDTO, ReviewOptions} from '../integration/domain/forecast-dto';
import {MeteorologicalStationDTO} from '../integration/domain/meteorological-station-dto';
import {ForecastService} from '../integration/services/forecast/forecast.service';
import {MenuItem, MessageService} from 'primeng/api';
import ReviewOptionsType = ReviewOptions.ReviewOptionsType;
import {LoadingSpinnerStore} from '../loading/store/loading-spinner.store';

@Component({
  selector: 'app-forecast',
  standalone: false,
  templateUrl: './forecast.component.html',
  styleUrl: './forecast.component.css',
  providers: [MessageService]
})
export class ForecastComponent {

  items: MenuItem[] = [];

  details: any[] = [];

  protected forecast: ForecastDTO;

  protected reviews = [
    {text: 'It was on point', value: ReviewOptionsType.BEST},
    {text: 'It was OK', value: ReviewOptionsType.GOOD},
    {text: 'Could be better', value: ReviewOptionsType.BAD},
    {text: 'Totally off point', value: ReviewOptionsType.WORST}
  ]

  private forecastService : ForecastService = inject(ForecastService);

  private messageService: MessageService = inject(MessageService);

  private loadingSpinnerStore: LoadingSpinnerStore = inject(LoadingSpinnerStore);

  private route: ActivatedRoute = inject(ActivatedRoute);

  constructor() {
    let station: MeteorologicalStationDTO = {
      id: "",
      name: "",
      code: "",
      latitude: 0,
      longitude: 0,
    }

    this.forecast = {
      id: "",
      meteorologicalStation: station,
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

    this.route.paramMap.subscribe(async (value) => {
      try {
        let id = value.get("id") ?? "";
        this.forecast = await this.forecastService.getById(id);

        this.initDetails()
      } catch (e) {
        this.messageService.add({
          severity: "error",
          summary: "View forecast failed",
          detail: "The forecast you chose could not be found!",
        });
      }
    });

    this.items = [
      {
        label: 'Home',
        icon: 'pi pi-home',
        route: "/",
      },
    ];
  }

  private initDetails() {
    this.details = [
      {
        label: "Temperature",
        icon: "pi pi-sun text-primary-900/90",// text-amber-300",
        value: this.forecast.temperature,
        unit: "°C"
      },
      {
        label: "Pressure",
        icon: "pi pi-sort-down-fill text-primary-900/90",
        value: this.forecast.pressure,
        unit: "hPa"
      },
      {
        label: "Relative Humidity",
        icon: "pi pi-lightbulb rotate-180 text-primary-900/90",//  text-blue-500",
        value: this.forecast.humidity,
        unit: "%"
      },
      {
        label: "Cloudiness",
        icon: "pi pi-cloud text-primary-900/90",// text-gray-300",
        value: this.forecast.cloudiness * 100 / 8,
        unit: "%"
      },
      {
        label: "Dew point temperature",
        icon: "pi pi-bullseye text-primary-900/90",// text-red-500",
        value: this.forecast.dewPoint,
        unit: "°C"
      },
      {
        label: "Wind direction",
        icon: "pi pi-compass text-primary-900/90",// text-sky-400",
        value: this.forecast.windDirection,
        unit: "°"
      },
      {
        label: "Wind speed",
        icon: "pi pi-gauge text-primary-900/90",// text-lime-400",
        value: this.forecast.windSpeed,
        unit: "m/s"
      },
    ]
  }

  getSeverity() {
    switch (this.forecast.review) {
      case ReviewOptionsType.WORST:
        return "danger";
      case ReviewOptionsType.BAD:
        return "warn";
      case ReviewOptionsType.GOOD:
        return "info";
      case ReviewOptionsType.BEST:
        return "success";
      default:
        return "";
    }
  }

  async changedReview() {
    try {
      this.loadingSpinnerStore.update({loading: true})

      this.forecast = await this.forecastService.update(this.forecast);
    } catch (e) {
      this.messageService.add({
        severity: "error",
        summary: "Review forecast failed",
        detail: "The review could not be saved!",
      });
    } finally {
      this.loadingSpinnerStore.update({loading: false})
    }
  }
}
