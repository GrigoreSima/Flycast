import {inject, Injectable} from '@angular/core';
import {SERVER_URL} from '../../../configs/configs';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {ForecastDTO} from '../../domain/forecast-dto';
import {promiseFromObservable} from '../../utils/utils';
import {MeteorologicalStationDTO} from '../../domain/meteorological-station-dto';

@Injectable({
  providedIn: 'root'
})
export class MeteorologicalStationService {

  private URL = `${SERVER_URL}/stations`;

  private http = inject(HttpClient);

  constructor() { }

  public getAll(): Promise<MeteorologicalStationDTO[]> {
    return promiseFromObservable(this.http.get<MeteorologicalStationDTO[]>(`${this.URL}`));
  }

  public getByName(name: string): Promise<MeteorologicalStationDTO> {
    return promiseFromObservable(this.http.get<MeteorologicalStationDTO>(`${this.URL}/${name}`));
  }
}
