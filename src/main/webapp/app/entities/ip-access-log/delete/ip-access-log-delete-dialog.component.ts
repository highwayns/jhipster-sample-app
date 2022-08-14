import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IIpAccessLog } from '../ip-access-log.model';
import { IpAccessLogService } from '../service/ip-access-log.service';

@Component({
  templateUrl: './ip-access-log-delete-dialog.component.html',
})
export class IpAccessLogDeleteDialogComponent {
  ipAccessLog?: IIpAccessLog;

  constructor(protected ipAccessLogService: IpAccessLogService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.ipAccessLogService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
