import {Component} from '@angular/core';
import {AuthenticationService} from "./services/authentication.service";
import {Router} from "@angular/router";
import {map} from "rxjs/operators";
import {NotificationService} from "./services/notification.service";

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  constructor(private loginservice: AuthenticationService, private router: Router, private notifyService: NotificationService) {
  }

  title = 'Easy Dynamic Accounting System';
  username = '';

  logout() {
    this.loginservice.logOut(this.username).pipe(
      map(userdata => {
        sessionStorage.removeItem("username");
        sessionStorage.removeItem("token");
        sessionStorage.removeItem("userId");
        sessionStorage.removeItem("userAdmin");
        this.notifyService.showInfo("Logged Out");
        this.router.navigate(['login']);
      })
    ).subscribe();
  }

  isloggedIn() {
    this.username = sessionStorage.getItem("username");
    return this.loginservice.isUserLoggedIn();
  }

}
