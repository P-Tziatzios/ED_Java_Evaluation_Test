import {Component, EventEmitter, Input, OnInit, Output, ViewChild} from '@angular/core';
import {MatPaginator, PageEvent} from "@angular/material/paginator";
import {Transaction, TransactionService} from "../services/transaction.service";
import {Account, AccountService} from "../services/account.service";
import {ActivatedRoute, Router} from "@angular/router";
import {MatSort, Sort} from "@angular/material/sort";
import {FormControl, FormGroup, Validators} from "@angular/forms";
import {MatTableDataSource} from "@angular/material/table";
import {tap} from "rxjs/operators";
import {User} from "../services/user.service";
import {NotificationService} from "../services/notification.service";

@Component({
  selector: 'app-account',
  templateUrl: './account.component.html',
  styleUrls: ['./account.component.css']
})
export class AccountComponent implements OnInit {

  displayedColumns: string[] = ['description', 'type', 'date', 'userName', 'amount', 'actions'];
  displayedColumnsUser: string[] = ['fName', 'sName'];

  @Input() dataLength: number;
  @Input() dataPageIndex: number = 0; // Which index is the current for the data paginator
  @Input() dataPageSize: number = 5; // Size of paginator page data
  @Input() pageSizeOptions: number[] = [5, 10, 25, 100]; // Size of paginator page data

  @Output() pageChange: EventEmitter<PageEvent> = new EventEmitter();

  constructor(private transactionService: TransactionService, private accountService: AccountService, private route: ActivatedRoute, private router: Router, private notifyService: NotificationService) {
  }

  @ViewChild(MatSort, {static: true}) sort: MatSort;
  @ViewChild(MatPaginator, {static: true}) paginator: MatPaginator;

  form: FormGroup;
  transactionList: MatTableDataSource<Transaction>;
  accountList: MatTableDataSource<Account>;
  userList: MatTableDataSource<User>;
  headers: string[];
  tableSort: string = '';
  tableSortDir: string = 'asc';
  filterValues: {
    description: string;
    type: string;
  };
  paramId: string;

  ngOnInit() {
    this.paramId = this.route.snapshot.params.id;
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
      calculatedBalance: new FormControl({value: '', disabled: true}),
      description: new FormControl({value: '', disabled: this.paramId}, [Validators.required, Validators.minLength(3)]),
    });
  }

  private populateMask() {
    this.getAccount(this.paramId).subscribe();
    this.filterValues = this.form.value;
    this.getTransactionsForAccount(this.tableSort, this.tableSortDir, this.dataPageIndex.toString(), this.dataPageSize.toString()).subscribe();
  }

  private populateMaskFromDraft() {
    this.accountService.createDraftAccount().pipe(
      tap((response: any) => {
        this.form.patchValue({...response.body});
      })
    ).subscribe();
  }

  getServerData(event: PageEvent) {
    this.dataPageIndex = event.pageIndex;
    this.dataPageSize = event.pageSize;
    return this.getTransactionsForAccount(this.tableSort, this.tableSortDir, this.dataPageIndex.toString(), this.dataPageSize.toString()).subscribe();
  }

  sortData(event: Sort) {
    if (event.direction == "") {
      this.tableSort = '';
      this.tableSortDir = 'asc';
    } else {
      this.tableSort = event.active
      this.tableSortDir = event.direction;
    }
    return this.getTransactionsForAccount(this.tableSort, this.tableSortDir, this.dataPageIndex.toString(), this.dataPageSize.toString()).subscribe();
  }

  private getTransactionsForAccount(sort: string, direction: string, page: string, size: string) {
    return this.transactionService.getAllForAccount(sort, direction, page, size, this.paramId).pipe(
      tap((response: any) => {
        const transactions: any = response.body.transactions;
        this.transactionList = new MatTableDataSource(transactions);
        this.dataLength = +response.body.totalItems;
        this.dataPageIndex = +response.body.currentPage;
      })
    );
  }

  private getAccount(paramId: string) {
    return this.accountService.getAccountById(paramId).pipe(
      tap((response: any) => {
        this.form.patchValue({...response.body});
        this.userList = new MatTableDataSource(response.body.users);
      })
    );
  }

  addTransaction() {
    this.router.navigate(['/addTransaction', this.paramId]);
  }

  editTransaction(id, $event: MouseEvent) {
    $event.stopPropagation();
    this.router.navigate(['/editTransaction', id]);
  }

  deleteTransaction(id, $event: MouseEvent) {
    $event.stopPropagation();
    this.transactionService.deleteTransaction(id).subscribe((data: any[]) => {
      this.populateMask();
    });
  }

  formSubmit(form: FormGroup) {
    if (form.valid) {
      this.form.markAsPristine();
      this.accountService.updateAccount(form.getRawValue()).subscribe(response => {
        this.notifyService.showSuccess("Save Account completed");
        this.router.navigate(['']);
      });
    } else {
      form.markAsDirty();
      this.notifyService.showError("Form is invalid");
    }
  }

  addUser() {
    this.notifyService.showWarning("Functionality not supported yet");
  }
}
