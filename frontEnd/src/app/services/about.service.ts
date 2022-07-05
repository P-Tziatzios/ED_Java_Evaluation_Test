import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {environment} from "../../environments/environment";

@Injectable({
  providedIn: 'root'
})
export class AboutService {

  constructor(private httpClient: HttpClient) {
  }

  getVersion(param: string) {
    return this.httpClient.get(
      environment.apiUrl + `/info/${param}`, {
        observe: 'response',
      })
  }
}
