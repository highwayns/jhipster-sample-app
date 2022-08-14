import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { IFeeSchedule, FeeSchedule } from '../fee-schedule.model';
import { FeeScheduleService } from '../service/fee-schedule.service';

@Component({
  selector: 'jhi-fee-schedule-update',
  templateUrl: './fee-schedule-update.component.html',
})
export class FeeScheduleUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    fee: [null, [Validators.required]],
    fromUsd: [null, [Validators.required]],
    toUsd: [null, [Validators.required]],
    order: [null, [Validators.required]],
    fee1: [null, [Validators.required]],
    globalBtc: [null, [Validators.required]],
  });

  constructor(protected feeScheduleService: FeeScheduleService, protected activatedRoute: ActivatedRoute, protected fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ feeSchedule }) => {
      this.updateForm(feeSchedule);
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const feeSchedule = this.createFromForm();
    if (feeSchedule.id !== undefined) {
      this.subscribeToSaveResponse(this.feeScheduleService.update(feeSchedule));
    } else {
      this.subscribeToSaveResponse(this.feeScheduleService.create(feeSchedule));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IFeeSchedule>>): void {
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

  protected updateForm(feeSchedule: IFeeSchedule): void {
    this.editForm.patchValue({
      id: feeSchedule.id,
      fee: feeSchedule.fee,
      fromUsd: feeSchedule.fromUsd,
      toUsd: feeSchedule.toUsd,
      order: feeSchedule.order,
      fee1: feeSchedule.fee1,
      globalBtc: feeSchedule.globalBtc,
    });
  }

  protected createFromForm(): IFeeSchedule {
    return {
      ...new FeeSchedule(),
      id: this.editForm.get(['id'])!.value,
      fee: this.editForm.get(['fee'])!.value,
      fromUsd: this.editForm.get(['fromUsd'])!.value,
      toUsd: this.editForm.get(['toUsd'])!.value,
      order: this.editForm.get(['order'])!.value,
      fee1: this.editForm.get(['fee1'])!.value,
      globalBtc: this.editForm.get(['globalBtc'])!.value,
    };
  }
}
