import {inject, Injectable} from '@angular/core';
import {SERVER_URL} from '../../../configs/configs';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {promiseFromObservable} from '../../utils/utils';
import {ForecastDTO} from '../../domain/forecast-dto';

@Injectable({
  providedIn: 'root'
})
export class ForecastService {

  private URL = `${SERVER_URL}/forecasts`;

  private http = inject(HttpClient);

  constructor() { }

  public getById(id: string): Promise<ForecastDTO> {
    return promiseFromObservable(this.http.get<ForecastDTO>(`${this.URL}/${id}`));
  }

  public update(forecast: ForecastDTO): Promise<ForecastDTO> {
    const headers = new HttpHeaders({"Access-Control-Allow-Origin": "*"});
    return promiseFromObservable(this.http.put<ForecastDTO>(`${this.URL}`, forecast, {headers: headers}));
  }

  public generate(username: string, stationName: string): Promise<ForecastDTO> {
    return promiseFromObservable(this.http.get<ForecastDTO>(`${this.URL}/prediction?client=${username}&station=${stationName}`));
  }
}
