import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { ICapture } from '../capture.model';
import { CaptureService } from '../service/capture.service';

@Component({
  templateUrl: './capture-delete-dialog.component.html',
})
export class CaptureDeleteDialogComponent {
  capture?: ICapture;

  constructor(protected captureService: CaptureService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.captureService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
