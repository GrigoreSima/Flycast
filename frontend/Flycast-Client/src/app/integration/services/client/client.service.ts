import {inject, Injectable} from '@angular/core';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {ClientDTO} from '../../domain/client-dto';
import {promiseFromObservable} from '../../utils/utils';
import {SERVER_URL} from '../../../configs/configs';
import {ForecastDTO} from '../../domain/forecast-dto';

@Injectable({
  providedIn: 'root'
})
export class ClientService {

  private URL = `${SERVER_URL}/clients`;

  private http = inject(HttpClient);

  constructor() { }

  public getById(id: string): Promise<ClientDTO> {
    return promiseFromObservable(this.http.get<ClientDTO>(`${this.URL}/${id}`));
  }

  // public getByName(name: string): Promise<ClientDTO> {
  //   return promiseFromObservable(this.http.get<ClientDTO>(`${this.URL}/${name}`));
  // }

  public update(client: ClientDTO): Promise<ClientDTO> {
    const headers = new HttpHeaders({"Access-Control-Allow-Origin": "*"});
    return promiseFromObservable(this.http.put<ClientDTO>(`${this.URL}`, client, {headers: headers}));
  }

  public getForecastsByClientId(id: string): Promise<ForecastDTO[]> {
    return promiseFromObservable(this.http.get<ForecastDTO[]>(`${this.URL}/${id}/forecasts`));
  }
}
