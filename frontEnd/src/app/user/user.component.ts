import {Component, OnInit} from '@angular/core';
import {UserService} from "../services/user.service";
import {Router} from "@angular/router";
import {tap} from "rxjs/operators";

@Component({
  selector: 'app-user',
  templateUrl: './user.component.html',
  styleUrls: ['./user.component.css']
})
export class UserComponent implements OnInit {
  users = [];

  constructor(private userService: UserService, private router: Router) {
  }

  ngOnInit(): void {
    this.populateMask();
  }

  private populateMask() {
    this.userService.getAll().pipe(
      tap((response: any) => {
        this.users = response.body;
      })
    ).subscribe();
  }

  editUser(useId) {
    this.router.navigate(['/editUser', useId]);
  }

  deleteUser(useId) {
    this.userService.deleteUser(useId).subscribe((data: any[]) => {
      this.router.navigate((['/home']));
    });
  }

  createUser() {
    this.router.navigate(['/newUser']);
  }
}
