import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IChangeSettings } from '../change-settings.model';
import { ChangeSettingsService } from '../service/change-settings.service';

@Component({
  templateUrl: './change-settings-delete-dialog.component.html',
})
export class ChangeSettingsDeleteDialogComponent {
  changeSettings?: IChangeSettings;

  constructor(protected changeSettingsService: ChangeSettingsService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.changeSettingsService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
