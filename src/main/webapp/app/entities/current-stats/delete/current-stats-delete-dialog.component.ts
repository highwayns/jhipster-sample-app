import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { ICurrentStats } from '../current-stats.model';
import { CurrentStatsService } from '../service/current-stats.service';

@Component({
  templateUrl: './current-stats-delete-dialog.component.html',
})
export class CurrentStatsDeleteDialogComponent {
  currentStats?: ICurrentStats;

  constructor(protected currentStatsService: CurrentStatsService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.currentStatsService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
