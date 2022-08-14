import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IApiKeys } from '../api-keys.model';
import { ApiKeysService } from '../service/api-keys.service';

@Component({
  templateUrl: './api-keys-delete-dialog.component.html',
})
export class ApiKeysDeleteDialogComponent {
  apiKeys?: IApiKeys;

  constructor(protected apiKeysService: ApiKeysService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.apiKeysService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
