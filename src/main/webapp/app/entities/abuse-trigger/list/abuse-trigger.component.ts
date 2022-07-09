import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IAbuseTrigger } from '../abuse-trigger.model';
import { AbuseTriggerService } from '../service/abuse-trigger.service';
import { AbuseTriggerDeleteDialogComponent } from '../delete/abuse-trigger-delete-dialog.component';

@Component({
  selector: 'jhi-abuse-trigger',
  templateUrl: './abuse-trigger.component.html',
})
export class AbuseTriggerComponent implements OnInit {
  abuseTriggers?: IAbuseTrigger[];
  isLoading = false;

  constructor(protected abuseTriggerService: AbuseTriggerService, protected modalService: NgbModal) {}

  loadAll(): void {
    this.isLoading = true;

    this.abuseTriggerService.query().subscribe({
      next: (res: HttpResponse<IAbuseTrigger[]>) => {
        this.isLoading = false;
        this.abuseTriggers = res.body ?? [];
      },
      error: () => {
        this.isLoading = false;
      },
    });
  }

  ngOnInit(): void {
    this.loadAll();
  }

  trackId(_index: number, item: IAbuseTrigger): number {
    return item.id!;
  }

  delete(abuseTrigger: IAbuseTrigger): void {
    const modalRef = this.modalService.open(AbuseTriggerDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.abuseTrigger = abuseTrigger;
    // unsubscribe not needed because closed completes on modal close
    modalRef.closed.subscribe(reason => {
      if (reason === 'deleted') {
        this.loadAll();
      }
    });
  }
}
