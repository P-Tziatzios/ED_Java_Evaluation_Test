import {Injectable} from '@angular/core';
import {HttpClient, HttpParams} from "@angular/common/http";
import {User} from "./user.service";
import {Observable} from "rxjs";
import {environment} from "../../environments/environment";

export interface Account {
  id: string;
  calculatedBalance: number;
  description: String;
  lastTransaction: Date;
  users: [User];
}

@Injectable({
  providedIn: 'root'
})
export class AccountService {


  constructor(private httpClient: HttpClient) {
  }

  getAll(userID: string) {
    return this.httpClient.get(
      environment.apiUrl + '/account', {
        observe: 'response',
        params: new HttpParams()
          .set('userID', userID)
      })
  }

  getAccountById(accountId: any) {
    return this.httpClient.get(
      `${environment.apiUrl + '/account'}/${accountId}`, {
        observe: 'response'
      })
  }

  createDraftAccount() {
    return this.httpClient.get(`${environment.apiUrl + '/account/_draft'}`, {
      observe: 'response'
    })
  }

  updateAccount(account: Account): Observable<Account> {
    return this.httpClient.put<Account>(`${environment.apiUrl + '/account'}`, account);
  }
}
