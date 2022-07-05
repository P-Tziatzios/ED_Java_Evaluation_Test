import {Component, Input, OnInit} from '@angular/core';
import {Router} from '@angular/router';
import {AuthenticationService} from "../services/authentication.service";
import {map} from "rxjs/operators";


@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

  username = ''
  password = ''
  invalidLogin = false

  @Input() error: string | null;

  constructor(private router: Router, private loginservice: AuthenticationService) {
  }

  ngOnInit() {
  }

  checkLogin() {
    this.loginservice.authenticate(this.username, this.password).pipe(
      map(userData => {
        sessionStorage.setItem("username", this.username);
        sessionStorage.setItem("token", userData.jwttoken);
        sessionStorage.setItem("userId", userData.userId);
        sessionStorage.setItem("userAdmin", userData.admin);
        this.router.navigate([''])
        this.invalidLogin = false
        return userData;
      })
    ).subscribe();
  }

}
