import {inject, Injectable} from '@angular/core';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {SERVER_URL} from '../../../configs/configs';
import {SignInResponse} from '../../utils/responses/sign-in-response';
import {SignInDTO} from '../../domain/sign-in-dto';
import {promiseFromObservable} from '../../utils/utils';
import {ClientDTO} from '../../domain/client-dto';
import {SignUpResponse} from '../../utils/responses/sign-up-response';
import {SignUpDTO} from '../../domain/sign-up-dto';

@Injectable({
  providedIn: 'root'
})
export class AuthenticationService {

  private URL = `${SERVER_URL}/auth`;

  private http = inject(HttpClient);

  constructor() {
  }

  public signIn(request: SignInDTO): Promise<SignInResponse> {
    const headers = new HttpHeaders({"Access-Control-Allow-Origin": "*"});
    return promiseFromObservable(this.http.post<SignInResponse>(`${this.URL}/sign-in`, request, {headers: headers}))
      .then<SignInResponse>((response: SignInResponse) => {
        localStorage.setItem("token", response.token);
        localStorage.setItem("client-id", response.id);
        return response;
      });
  }

  public signUp(request: SignUpDTO): Promise<SignInResponse> {
    const headers = new HttpHeaders({"Access-Control-Allow-Origin": "*"});
    return promiseFromObservable(this.http.post<SignInResponse>(`${this.URL}/sign-up`, request, {headers: headers}))
      .then<SignInResponse>((response: SignInResponse) => {
        localStorage.setItem("token", response.token);
        localStorage.setItem("client-id", response.id);
        return response;
      });
  }

  public signOut(): void {
    localStorage.removeItem("token");
    localStorage.removeItem("client-id");
  }

  public isLoggedIn(): boolean {

    let token = localStorage.getItem("token");

    if (token == null) return false;

    return token.length != 0;
  }
}
