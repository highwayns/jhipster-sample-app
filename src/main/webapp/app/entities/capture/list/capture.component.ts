import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { ICapture } from '../capture.model';
import { CaptureService } from '../service/capture.service';
import { CaptureDeleteDialogComponent } from '../delete/capture-delete-dialog.component';

@Component({
  selector: 'jhi-capture',
  templateUrl: './capture.component.html',
})
export class CaptureComponent implements OnInit {
  captures?: ICapture[];
  isLoading = false;

  constructor(protected captureService: CaptureService, protected modalService: NgbModal) {}

  loadAll(): void {
    this.isLoading = true;

    this.captureService.query().subscribe({
      next: (res: HttpResponse<ICapture[]>) => {
        this.isLoading = false;
        this.captures = res.body ?? [];
      },
      error: () => {
        this.isLoading = false;
      },
    });
  }

  ngOnInit(): void {
    this.loadAll();
  }

  trackId(_index: number, item: ICapture): number {
    return item.id!;
  }

  delete(capture: ICapture): void {
    const modalRef = this.modalService.open(CaptureDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.capture = capture;
    // unsubscribe not needed because closed completes on modal close
    modalRef.closed.subscribe(reason => {
      if (reason === 'deleted') {
        this.loadAll();
      }
    });
  }
}
