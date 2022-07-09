import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IIssuer } from '../issuer.model';
import { IssuerService } from '../service/issuer.service';

@Component({
  templateUrl: './issuer-delete-dialog.component.html',
})
export class IssuerDeleteDialogComponent {
  issuer?: IIssuer;

  constructor(protected issuerService: IssuerService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: string): void {
    this.issuerService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
