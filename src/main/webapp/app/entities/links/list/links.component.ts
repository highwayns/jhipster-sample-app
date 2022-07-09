import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { ILinks } from '../links.model';
import { LinksService } from '../service/links.service';
import { LinksDeleteDialogComponent } from '../delete/links-delete-dialog.component';

@Component({
  selector: 'jhi-links',
  templateUrl: './links.component.html',
})
export class LinksComponent implements OnInit {
  links?: ILinks[];
  isLoading = false;

  constructor(protected linksService: LinksService, protected modalService: NgbModal) {}

  loadAll(): void {
    this.isLoading = true;

    this.linksService.query().subscribe({
      next: (res: HttpResponse<ILinks[]>) => {
        this.isLoading = false;
        this.links = res.body ?? [];
      },
      error: () => {
        this.isLoading = false;
      },
    });
  }

  ngOnInit(): void {
    this.loadAll();
  }

  trackId(_index: number, item: ILinks): number {
    return item.id!;
  }

  delete(links: ILinks): void {
    const modalRef = this.modalService.open(LinksDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.links = links;
    // unsubscribe not needed because closed completes on modal close
    modalRef.closed.subscribe(reason => {
      if (reason === 'deleted') {
        this.loadAll();
      }
    });
  }
}
