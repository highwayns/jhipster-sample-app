import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IRefund } from '../refund.model';
import { RefundService } from '../service/refund.service';
import { RefundDeleteDialogComponent } from '../delete/refund-delete-dialog.component';

@Component({
  selector: 'jhi-refund',
  templateUrl: './refund.component.html',
})
export class RefundComponent implements OnInit {
  refunds?: IRefund[];
  isLoading = false;

  constructor(protected refundService: RefundService, protected modalService: NgbModal) {}

  loadAll(): void {
    this.isLoading = true;

    this.refundService.query().subscribe({
      next: (res: HttpResponse<IRefund[]>) => {
        this.isLoading = false;
        this.refunds = res.body ?? [];
      },
      error: () => {
        this.isLoading = false;
      },
    });
  }

  ngOnInit(): void {
    this.loadAll();
  }

  trackId(_index: number, item: IRefund): number {
    return item.id!;
  }

  delete(refund: IRefund): void {
    const modalRef = this.modalService.open(RefundDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.refund = refund;
    // unsubscribe not needed because closed completes on modal close
    modalRef.closed.subscribe(reason => {
      if (reason === 'deleted') {
        this.loadAll();
      }
    });
  }
}
