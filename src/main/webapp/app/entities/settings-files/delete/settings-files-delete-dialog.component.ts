import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { ISettingsFiles } from '../settings-files.model';
import { SettingsFilesService } from '../service/settings-files.service';

@Component({
  templateUrl: './settings-files-delete-dialog.component.html',
})
export class SettingsFilesDeleteDialogComponent {
  settingsFiles?: ISettingsFiles;

  constructor(protected settingsFilesService: SettingsFilesService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.settingsFilesService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
