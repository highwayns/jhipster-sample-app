import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IRecurrenceCriteria } from '../recurrence-criteria.model';
import { RecurrenceCriteriaService } from '../service/recurrence-criteria.service';
import { RecurrenceCriteriaDeleteDialogComponent } from '../delete/recurrence-criteria-delete-dialog.component';

@Component({
  selector: 'jhi-recurrence-criteria',
  templateUrl: './recurrence-criteria.component.html',
})
export class RecurrenceCriteriaComponent implements OnInit {
  recurrenceCriteria?: IRecurrenceCriteria[];
  isLoading = false;

  constructor(protected recurrenceCriteriaService: RecurrenceCriteriaService, protected modalService: NgbModal) {}

  loadAll(): void {
    this.isLoading = true;

    this.recurrenceCriteriaService.query().subscribe({
      next: (res: HttpResponse<IRecurrenceCriteria[]>) => {
        this.isLoading = false;
        this.recurrenceCriteria = res.body ?? [];
      },
      error: () => {
        this.isLoading = false;
      },
    });
  }

  ngOnInit(): void {
    this.loadAll();
  }

  trackId(_index: number, item: IRecurrenceCriteria): number {
    return item.id!;
  }

  delete(recurrenceCriteria: IRecurrenceCriteria): void {
    const modalRef = this.modalService.open(RecurrenceCriteriaDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.recurrenceCriteria = recurrenceCriteria;
    // unsubscribe not needed because closed completes on modal close
    modalRef.closed.subscribe(reason => {
      if (reason === 'deleted') {
        this.loadAll();
      }
    });
  }
}
