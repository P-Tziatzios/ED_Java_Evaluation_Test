import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {HomeComponent} from "./home/home.component";
import {AboutComponent} from "./about/about.component";
import {UserComponent} from "./user/user.component";
import {EditUserComponent} from "./user/edit-user/edit-user.component";
import {LoginComponent} from "./login/login.component";
import {AuthGaurdService} from "./services/auth-gaurd.service";
import {AccountComponent} from "./account/account.component";
import {TransactionComponent} from "./transaction/transaction.component";

const routes: Routes = [
  {path: '', redirectTo: 'home', pathMatch: 'full'},
  {path: 'login', component: LoginComponent},
  {path: 'home', component: HomeComponent, canActivate: [AuthGaurdService]},
  {path: 'user', component: UserComponent, canActivate: [AuthGaurdService]},
  {path: 'newAccount', component: AccountComponent, canActivate: [AuthGaurdService]},
  {path: 'newUser', component: EditUserComponent, canActivate: [AuthGaurdService]},
  {path: 'editUser/:id', component: EditUserComponent, canActivate: [AuthGaurdService]},
  {path: 'viewAccount/:id', component: AccountComponent, canActivate: [AuthGaurdService]},
  {path: 'addTransaction/:accountId', component: TransactionComponent, canActivate: [AuthGaurdService]},
  {path: 'editTransaction/:transactionId', component: TransactionComponent, canActivate: [AuthGaurdService]},
  {path: 'about', component: AboutComponent},
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule {
}
