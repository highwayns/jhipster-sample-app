import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IInnodbLockMonitor } from '../innodb-lock-monitor.model';
import { InnodbLockMonitorService } from '../service/innodb-lock-monitor.service';

@Component({
  templateUrl: './innodb-lock-monitor-delete-dialog.component.html',
})
export class InnodbLockMonitorDeleteDialogComponent {
  innodbLockMonitor?: IInnodbLockMonitor;

  constructor(protected innodbLockMonitorService: InnodbLockMonitorService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.innodbLockMonitorService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
