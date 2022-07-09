import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { ILinks } from '../links.model';
import { LinksService } from '../service/links.service';

@Component({
  templateUrl: './links-delete-dialog.component.html',
})
export class LinksDeleteDialogComponent {
  links?: ILinks;

  constructor(protected linksService: LinksService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.linksService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
