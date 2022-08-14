import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { IInnodbLockMonitor, InnodbLockMonitor } from '../innodb-lock-monitor.model';
import { InnodbLockMonitorService } from '../service/innodb-lock-monitor.service';

@Component({
  selector: 'jhi-innodb-lock-monitor-update',
  templateUrl: './innodb-lock-monitor-update.component.html',
})
export class InnodbLockMonitorUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    a: [],
  });

  constructor(
    protected innodbLockMonitorService: InnodbLockMonitorService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ innodbLockMonitor }) => {
      this.updateForm(innodbLockMonitor);
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const innodbLockMonitor = this.createFromForm();
    if (innodbLockMonitor.id !== undefined) {
      this.subscribeToSaveResponse(this.innodbLockMonitorService.update(innodbLockMonitor));
    } else {
      this.subscribeToSaveResponse(this.innodbLockMonitorService.create(innodbLockMonitor));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IInnodbLockMonitor>>): void {
    result.pipe(finalize(() => this.onSaveFinalize())).subscribe({
      next: () => this.onSaveSuccess(),
      error: () => this.onSaveError(),
    });
  }

  protected onSaveSuccess(): void {
    this.previousState();
  }

  protected onSaveError(): void {
    // Api for inheritance.
  }

  protected onSaveFinalize(): void {
    this.isSaving = false;
  }

  protected updateForm(innodbLockMonitor: IInnodbLockMonitor): void {
    this.editForm.patchValue({
      id: innodbLockMonitor.id,
      a: innodbLockMonitor.a,
    });
  }

  protected createFromForm(): IInnodbLockMonitor {
    return {
      ...new InnodbLockMonitor(),
      id: this.editForm.get(['id'])!.value,
      a: this.editForm.get(['a'])!.value,
    };
  }
}
