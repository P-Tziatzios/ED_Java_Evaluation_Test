import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {environment} from "../../environments/environment";

@Injectable({
  providedIn: 'root'
})
export class AuthenticationService {

  constructor(private httpClient: HttpClient) {
  }

  authenticate(username, password) {
    return this.httpClient.post<any>(environment.apiUrl + "/authenticate", {username, password});
  }

  isUserLoggedIn() {
    let toekn = sessionStorage.getItem("token");
    return !(toekn === null);
  }

  logOut(username) {
    return this.httpClient.post<any>(environment.apiUrl + "/logout", {username});
  }
}
