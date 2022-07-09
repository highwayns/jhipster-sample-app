import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IResultAttributes } from '../result-attributes.model';
import { ResultAttributesService } from '../service/result-attributes.service';
import { ResultAttributesDeleteDialogComponent } from '../delete/result-attributes-delete-dialog.component';

@Component({
  selector: 'jhi-result-attributes',
  templateUrl: './result-attributes.component.html',
})
export class ResultAttributesComponent implements OnInit {
  resultAttributes?: IResultAttributes[];
  isLoading = false;

  constructor(protected resultAttributesService: ResultAttributesService, protected modalService: NgbModal) {}

  loadAll(): void {
    this.isLoading = true;

    this.resultAttributesService.query().subscribe({
      next: (res: HttpResponse<IResultAttributes[]>) => {
        this.isLoading = false;
        this.resultAttributes = res.body ?? [];
      },
      error: () => {
        this.isLoading = false;
      },
    });
  }

  ngOnInit(): void {
    this.loadAll();
  }

  trackId(_index: number, item: IResultAttributes): number {
    return item.id!;
  }

  delete(resultAttributes: IResultAttributes): void {
    const modalRef = this.modalService.open(ResultAttributesDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.resultAttributes = resultAttributes;
    // unsubscribe not needed because closed completes on modal close
    modalRef.closed.subscribe(reason => {
      if (reason === 'deleted') {
        this.loadAll();
      }
    });
  }
}
