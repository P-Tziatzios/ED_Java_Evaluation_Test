import {Injectable} from '@angular/core';
import {ToastrService} from "ngx-toastr";

@Injectable({
  providedIn: 'root'
})
export class NotificationService {

  constructor(private toastr: ToastrService) {
  }

  showSuccess(message) {
    this.toastr.success(message, "Success")
  }

  showError(message) {
    this.toastr.error(message, "Error")
  }

  showErrorWithTitle(message, title) {
    this.toastr.error(message, title)
  }

  showInfo(message) {
    this.toastr.info(message, "Info")
  }

  showWarning(message) {
    this.toastr.warning(message, "Warning")
  }
}
