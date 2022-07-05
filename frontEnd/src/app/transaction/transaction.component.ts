import {Component, OnInit} from '@angular/core';
import {FormControl, FormGroup, Validators} from "@angular/forms";
import {ActivatedRoute, Router} from "@angular/router";
import {Transaction, TransactionService} from "../services/transaction.service";
import {tap} from "rxjs/operators";
import {NotificationService} from "../services/notification.service";

@Component({
  selector: 'app-transaction',
  templateUrl: './transaction.component.html',
  styleUrls: ['./transaction.component.css']
})
export class TransactionComponent implements OnInit {

  constructor(private transactionService: TransactionService, private router: Router, private route: ActivatedRoute, private notifyService: NotificationService) {
  }

  public transaction: Transaction;
  form: FormGroup;
  types = ['EXPENCE', 'INCOME'];

  ngOnInit(): void {
    this.createForm();
    if (this.route.snapshot.params.accountId) {
      this.populateMaskFromDraft();
    } else if (this.route.snapshot.params.transactionId) {
      this.populateMask();
    }
  }

  private createForm() {
    this.form = new FormGroup({
      id: new FormControl(null),
      amount: new FormControl({value: 0}, Validators.required),
      date: new FormControl({value: '', disabled: true}, Validators.required),
      description: new FormControl({value: '', disabled: false}, [Validators.required, Validators.minLength(3)]),
      type: new FormControl(null, Validators.required),
      userName: new FormControl({value: '', disabled: true}),
      accountName: new FormControl({value: '', disabled: true}),
    });
  }

  private populateMask() {
    this.transactionService.getTransactionById(this.route.snapshot.params.transactionId).pipe(
      tap((response: any) => {
        this.form.patchValue({...response.body});
      })
    ).subscribe();
  }

  private populateMaskFromDraft() {
    this.transactionService.createDraftTransaction(this.route.snapshot.params.accountId).pipe(
      tap((response: any) => {
        this.form.patchValue({...response.body});
      })
    ).subscribe();
  }

  formSubmit(form: FormGroup) {
    if (form.valid) {
      this.transaction = form.getRawValue();
      this.transaction.userName = sessionStorage.getItem('username');
      this.form.markAsPristine();
      this.transactionService.updateTransaction(this.transaction).subscribe(response => {
        this.notifyService.showSuccess("Save Transaction completed");
        if (this.route.snapshot.params.accountId) {
          this.transactionService.createDraftTransaction(this.route.snapshot.params.accountId).subscribe(response => {
            this.router.navigate(['/viewAccount', this.route.snapshot.params.accountId]);
          });
        } else if (this.route.snapshot.params.transactionId) {
          this.transactionService.getTransactionById(this.route.snapshot.params.transactionId).subscribe(response => {
            this.router.navigate(['/editTransaction', this.route.snapshot.params.transactionId]);
          });
        }
      });
    } else {
      form.markAsDirty();
      this.notifyService.showError("Form is invalid");
    }
  }

}
