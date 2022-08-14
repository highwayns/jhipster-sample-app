import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IContentFiles } from '../content-files.model';
import { ContentFilesService } from '../service/content-files.service';

@Component({
  templateUrl: './content-files-delete-dialog.component.html',
})
export class ContentFilesDeleteDialogComponent {
  contentFiles?: IContentFiles;

  constructor(protected contentFilesService: ContentFilesService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.contentFilesService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
