import {Injectable} from '@angular/core';
import {HttpErrorResponse, HttpHandler, HttpInterceptor, HttpRequest} from "@angular/common/http";
import {catchError} from "rxjs/operators";
import {throwError} from "rxjs";
import {NotificationService} from "./notification.service";

@Injectable({
  providedIn: 'root'
})
export class BasicAuthHtppInterceptorService implements HttpInterceptor {

  constructor(private notifyService: NotificationService) {
  }

  headerNameAuth = 'SBAFront';

  intercept(req: HttpRequest<any>, next: HttpHandler) {
    if (sessionStorage.getItem('username') && sessionStorage.getItem('token')) {
      req = req.clone({
        setHeaders: {
          Authorization: this.headerNameAuth + sessionStorage.getItem('token'),
        }
      })
    }
    return next.handle(req).pipe(
      catchError((error: HttpErrorResponse) => {
        let errorMsg = '';
        if (error.error instanceof ErrorEvent) {
          errorMsg = `Error: ${error.error.message}`;
          this.notifyService.showErrorWithTitle(errorMsg, 'Client side error');
        } else {
          errorMsg = `Error Code: ${error.status},  Message: ${error.message}`;
          this.notifyService.showErrorWithTitle(errorMsg, 'Server side error');
        }
        return throwError(errorMsg);
      })
    )

  }
}
