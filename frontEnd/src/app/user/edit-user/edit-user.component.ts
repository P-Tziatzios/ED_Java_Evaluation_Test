import {Component, OnInit} from '@angular/core';
import {FormArray, FormControl, FormGroup, Validators} from "@angular/forms";
import {User, UserService} from "../../services/user.service";
import {ActivatedRoute, Router} from "@angular/router";
import {tap} from "rxjs/operators";
import {NotificationService} from "../../services/notification.service";

@Component({
  selector: 'app-edit-user',
  templateUrl: './edit-user.component.html',
  styleUrls: ['./edit-user.component.css']
})
export class EditUserComponent implements OnInit {

  form: FormGroup;

  constructor(private userServie: UserService, private router: Router, private route: ActivatedRoute, private notifyService: NotificationService) {
  }

  public user: User;
  public isDraft: boolean;
  paramId: string;

  ngOnInit(): void {
    this.paramId = this.route.snapshot.params.id;
    this.isDraft = false;
    this.createForm();
    if (this.paramId) {
      this.populateMask();
    } else {
      this.populateMaskFromDraft();
    }
  }

  private createForm() {
    this.form = new FormGroup({
      id: new FormControl(null),
      userName: new FormControl(null, [Validators.required, Validators.minLength(3)]),
      fistName: new FormControl(null, [Validators.required, Validators.minLength(3)]),
      surName: new FormControl(null, Validators.required),
      comments: new FormControl(null),
      salt: new FormControl(null),
      pass: new FormControl(null, Validators.required),
      superAdmin: new FormControl(null),
      permissions: new FormArray([])
    });
  }

  private populateMask() {
    this.userServie.getUserById(this.paramId).pipe(
      tap((response: any) => {
        this.form.patchValue({...response.body});
      })
    ).subscribe();
  }

  private populateMaskFromDraft() {
    this.userServie.createDraftUser().pipe(
      tap((response: any) => {
        this.form.patchValue({...response.body});
        this.isDraft = true;
      })
    ).subscribe();
  }

  formSubmit(form: FormGroup) {
    if (form.valid) {
      this.user = form.value;
      this.form.markAsPristine();
      this.userServie.updateUser(this.user).subscribe(response => {
        this.notifyService.showSuccess("Save User completed");
        this.router.navigate(['']);
      });
    } else {
      form.markAsDirty();
      this.notifyService.showError("Form is invalid");
    }

  }
}
