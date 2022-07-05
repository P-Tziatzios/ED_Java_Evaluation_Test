import {Component, EventEmitter, Input, OnInit, Output, ViewChild} from '@angular/core';
import {MatPaginator, PageEvent} from "@angular/material/paginator";
import {MatSort, Sort} from "@angular/material/sort";
import {FormControl, FormGroup} from "@angular/forms";
import {MatTableDataSource} from "@angular/material/table";
import {Transaction, TransactionService} from "../services/transaction.service";
import {tap} from "rxjs/operators";
import {Account, AccountService} from "../services/account.service";
import {Router} from "@angular/router";

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit {
  displayedColumns: string[] = ['description', 'type', 'date', 'account', 'amount'];
  displayedColumnsAccount: string[] = ['description', 'calculatedBalance'];

  @Input() dataLength: number;
  @Input() dataPageIndex: number = 0; // Which index is the current for the data paginator
  @Input() dataPageSize: number = 5; // Size of paginator page data
  @Input() pageSizeOptions: number[] = [5, 10, 25, 100]; // Size of paginator page data

  @Output() pageChange: EventEmitter<PageEvent> = new EventEmitter();

  constructor(private transactionService: TransactionService, private accountService: AccountService, private router: Router) {
  }

  @ViewChild(MatSort, {static: true}) sort: MatSort;
  @ViewChild(MatPaginator, {static: true}) paginator: MatPaginator;

  form: FormGroup;
  transactionList: MatTableDataSource<Transaction>;
  accountList: MatTableDataSource<Account>;
  headers: string[];
  headersAccount: string[];
  tableSort: string = '';
  tableSortDir: string = 'asc';
  filterValues: {
    description: string;
    type: string;
  };
  private userID: string;
  isAdmin: boolean = false;

  ngOnInit() {
    this.userID = sessionStorage.getItem("username");
    this.isAdmin = (/true/i).test(sessionStorage.getItem("userAdmin"));
    this.createFilterForm();
    this.filterValues = this.form.value;
    this.populateMask();
  }

  private populateMask() {
    this.getTransactionsForUser(this.tableSort, this.tableSortDir, this.dataPageIndex.toString(), this.dataPageSize.toString(), this.filterValues.description, this.filterValues.type).subscribe();
    this.getAccountsForUser().subscribe();
  }

  applyFilter(form: FormGroup) {
    this.filterValues = form.value;
    return this.getTransactionsForUser(this.tableSort, this.tableSortDir, this.dataPageIndex.toString(), this.dataPageSize.toString(), this.filterValues.description, this.filterValues.type).subscribe();
  }

  clearFilter() {
    this.createFilterForm();
    this.filterValues = this.form.value;
    this.getTransactionsForUser(this.tableSort, this.tableSortDir, this.dataPageIndex.toString(), this.dataPageSize.toString(), this.filterValues.description, this.filterValues.type).subscribe();
  }

  private createFilterForm() {
    this.form = new FormGroup({
      id: new FormControl(''),
      amount: new FormControl(''),
      date: new FormControl(''),
      description: new FormControl(''),
      type: new FormControl(''),
      userName: new FormControl(''),
      accountName: new FormControl(''),
    });
  }


  sortData(event: Sort) {
    if (event.direction == "") {
      this.tableSort = '';
      this.tableSortDir = 'asc';
    } else {
      this.tableSort = event.active
      this.tableSortDir = event.direction;
    }
    return this.getTransactionsForUser(this.tableSort, this.tableSortDir, this.dataPageIndex.toString(), this.dataPageSize.toString(), this.filterValues.description, this.filterValues.type).subscribe();
  }

  getServerData(event: PageEvent) {
    this.dataPageIndex = event.pageIndex;
    this.dataPageSize = event.pageSize;
    return this.getTransactionsForUser(this.tableSort, this.tableSortDir, this.dataPageIndex.toString(), this.dataPageSize.toString(), this.filterValues.description, this.filterValues.type).subscribe();
  }

  private getTransactionsForUser(sort: string, direction: string, page: string, size: string, description: string, type: string) {
    return this.transactionService.getAllForUser(sort, direction, page, size, description, type, this.userID).pipe(
      tap((response: any) => {
        const transactions: any = response.body.transactions;
        this.transactionList = new MatTableDataSource(transactions);
        this.dataLength = +response.body.totalItems;
        this.dataPageIndex = +response.body.currentPage;
      })
    );
  }

  public getaccounts(event) {
    if (event.index == 1) {
      this.getallAccounts().subscribe();
    } else if (event.index == 0) {
      this.getAccountsForUser().subscribe();
    }

  }

  private getallAccounts() {
    return this.accountService.getAll(null).pipe(
      tap((response: any) => {
        const accounts: any = response.body.accounts;
        this.accountList = new MatTableDataSource(accounts);
      })
    );
  }

  private getAccountsForUser() {
    return this.accountService.getAll(this.userID).pipe(
      tap((response: any) => {
        const accounts: any = response.body.accounts;
        this.accountList = new MatTableDataSource(accounts);
      })
    );
  }

  viewAccount(id, $event: MouseEvent) {
    $event.stopPropagation();
    this.router.navigate(['/viewAccount', id]);
  }

  editTransaction(id, $event: MouseEvent) {
    $event.stopPropagation();
    this.router.navigate(['/editTransaction', id]);
  }
}
