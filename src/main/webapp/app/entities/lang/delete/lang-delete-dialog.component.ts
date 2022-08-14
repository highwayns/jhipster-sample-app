import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { ILang } from '../lang.model';
import { LangService } from '../service/lang.service';

@Component({
  templateUrl: './lang-delete-dialog.component.html',
})
export class LangDeleteDialogComponent {
  lang?: ILang;

  constructor(protected langService: LangService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.langService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
