import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IIssuer } from '../issuer.model';
import { IssuerService } from '../service/issuer.service';
import { IssuerDeleteDialogComponent } from '../delete/issuer-delete-dialog.component';

@Component({
  selector: 'jhi-issuer',
  templateUrl: './issuer.component.html',
})
export class IssuerComponent implements OnInit {
  issuers?: IIssuer[];
  isLoading = false;

  constructor(protected issuerService: IssuerService, protected modalService: NgbModal) {}

  loadAll(): void {
    this.isLoading = true;

    this.issuerService.query().subscribe({
      next: (res: HttpResponse<IIssuer[]>) => {
        this.isLoading = false;
        this.issuers = res.body ?? [];
      },
      error: () => {
        this.isLoading = false;
      },
    });
  }

  ngOnInit(): void {
    this.loadAll();
  }

  trackId(_index: number, item: IIssuer): string {
    return item.id!;
  }

  delete(issuer: IIssuer): void {
    const modalRef = this.modalService.open(IssuerDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.issuer = issuer;
    // unsubscribe not needed because closed completes on modal close
    modalRef.closed.subscribe(reason => {
      if (reason === 'deleted') {
        this.loadAll();
      }
    });
  }
}
