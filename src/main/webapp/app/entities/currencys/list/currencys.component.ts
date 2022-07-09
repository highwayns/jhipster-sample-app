import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { ICurrencys } from '../currencys.model';
import { CurrencysService } from '../service/currencys.service';
import { CurrencysDeleteDialogComponent } from '../delete/currencys-delete-dialog.component';

@Component({
  selector: 'jhi-currencys',
  templateUrl: './currencys.component.html',
})
export class CurrencysComponent implements OnInit {
  currencys?: ICurrencys[];
  isLoading = false;

  constructor(protected currencysService: CurrencysService, protected modalService: NgbModal) {}

  loadAll(): void {
    this.isLoading = true;

    this.currencysService.query().subscribe({
      next: (res: HttpResponse<ICurrencys[]>) => {
        this.isLoading = false;
        this.currencys = res.body ?? [];
      },
      error: () => {
        this.isLoading = false;
      },
    });
  }

  ngOnInit(): void {
    this.loadAll();
  }

  trackId(_index: number, item: ICurrencys): number {
    return item.id!;
  }

  delete(currencys: ICurrencys): void {
    const modalRef = this.modalService.open(CurrencysDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.currencys = currencys;
    // unsubscribe not needed because closed completes on modal close
    modalRef.closed.subscribe(reason => {
      if (reason === 'deleted') {
        this.loadAll();
      }
    });
  }
}
