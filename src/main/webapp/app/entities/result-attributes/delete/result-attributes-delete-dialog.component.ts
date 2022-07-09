import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IResultAttributes } from '../result-attributes.model';
import { ResultAttributesService } from '../service/result-attributes.service';

@Component({
  templateUrl: './result-attributes-delete-dialog.component.html',
})
export class ResultAttributesDeleteDialogComponent {
  resultAttributes?: IResultAttributes;

  constructor(protected resultAttributesService: ResultAttributesService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.resultAttributesService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
