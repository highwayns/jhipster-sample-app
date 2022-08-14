import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IHistoricalData } from '../historical-data.model';
import { HistoricalDataService } from '../service/historical-data.service';

@Component({
  templateUrl: './historical-data-delete-dialog.component.html',
})
export class HistoricalDataDeleteDialogComponent {
  historicalData?: IHistoricalData;

  constructor(protected historicalDataService: HistoricalDataService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.historicalDataService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
