import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IParameters } from '../parameters.model';
import { ParametersService } from '../service/parameters.service';
import { ParametersDeleteDialogComponent } from '../delete/parameters-delete-dialog.component';

@Component({
  selector: 'jhi-parameters',
  templateUrl: './parameters.component.html',
})
export class ParametersComponent implements OnInit {
  parameters?: IParameters[];
  isLoading = false;

  constructor(protected parametersService: ParametersService, protected modalService: NgbModal) {}

  loadAll(): void {
    this.isLoading = true;

    this.parametersService.query().subscribe({
      next: (res: HttpResponse<IParameters[]>) => {
        this.isLoading = false;
        this.parameters = res.body ?? [];
      },
      error: () => {
        this.isLoading = false;
      },
    });
  }

  ngOnInit(): void {
    this.loadAll();
  }

  trackId(_index: number, item: IParameters): number {
    return item.id!;
  }

  delete(parameters: IParameters): void {
    const modalRef = this.modalService.open(ParametersDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.parameters = parameters;
    // unsubscribe not needed because closed completes on modal close
    modalRef.closed.subscribe(reason => {
      if (reason === 'deleted') {
        this.loadAll();
      }
    });
  }
}
