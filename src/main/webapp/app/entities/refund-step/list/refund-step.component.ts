import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IRefundStep } from '../refund-step.model';
import { RefundStepService } from '../service/refund-step.service';
import { RefundStepDeleteDialogComponent } from '../delete/refund-step-delete-dialog.component';

@Component({
  selector: 'jhi-refund-step',
  templateUrl: './refund-step.component.html',
})
export class RefundStepComponent implements OnInit {
  refundSteps?: IRefundStep[];
  isLoading = false;

  constructor(protected refundStepService: RefundStepService, protected modalService: NgbModal) {}

  loadAll(): void {
    this.isLoading = true;

    this.refundStepService.query().subscribe({
      next: (res: HttpResponse<IRefundStep[]>) => {
        this.isLoading = false;
        this.refundSteps = res.body ?? [];
      },
      error: () => {
        this.isLoading = false;
      },
    });
  }

  ngOnInit(): void {
    this.loadAll();
  }

  trackId(_index: number, item: IRefundStep): number {
    return item.id!;
  }

  delete(refundStep: IRefundStep): void {
    const modalRef = this.modalService.open(RefundStepDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.refundStep = refundStep;
    // unsubscribe not needed because closed completes on modal close
    modalRef.closed.subscribe(reason => {
      if (reason === 'deleted') {
        this.loadAll();
      }
    });
  }
}
