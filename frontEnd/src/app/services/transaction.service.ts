import {Injectable} from '@angular/core';
import {HttpClient, HttpParams} from "@angular/common/http";
import {Observable} from "rxjs";
import {environment} from "../../environments/environment";

export interface Transaction {
  id: string;
  amount: number;
  date: Date;
  description: string;
  type: string;
  userName: string;
  accountName: string;
}

@Injectable({
  providedIn: 'root'
})
export class TransactionService {

  constructor(private httpClient: HttpClient) {
  }

  getAllForUser(sort: string, direction: string, page: string, size: string, description: string, type: string, userID: string) {
    return this.httpClient.get(
      environment.apiUrl + '/transactions/getAllForUser', {
        observe: 'response',
        params: new HttpParams()
          .set('sort', sort)
          .set('direction', direction)
          .set('page', page)
          .set('size', size)
          .set('description', description)
          .set('type', type)
          .set('userID', userID)
      })
  }

  getAllForAccount(sort: string, direction: string, page: string, size: string, accountId: string) {
    return this.httpClient.get(
      environment.apiUrl + '/transactions/getAllForAccount', {
        observe: 'response',
        params: new HttpParams()
          .set('sort', sort)
          .set('direction', direction)
          .set('page', page)
          .set('size', size)
          .set('accountId', accountId)
      })
  }

  deleteTransaction(transactionId) {
    return this.httpClient.delete(
      `${environment.apiUrl + '/transactions'}/${transactionId}`);
  }

  updateTransaction(transaction: Transaction): Observable<Transaction> {
    return this.httpClient.put<Transaction>(`${environment.apiUrl + '/transactions'}`, transaction);
  }

  createDraftTransaction(accountId) {
    return this.httpClient.get(
      `${environment.apiUrl + '/transactions/_draft'}/${accountId}`, {
        observe: 'response'
      })
  }

  getTransactionById(transactionId: any) {
    return this.httpClient.get(`${environment.apiUrl + '/transactions'}/${transactionId}`, {
      observe: 'response'
    })
  }
}
