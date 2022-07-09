import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import dayjs from 'dayjs/esm';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';

import { ICapture, Capture } from '../capture.model';
import { CaptureService } from '../service/capture.service';
import { CaptureStatus } from 'app/entities/enumerations/capture-status.model';
import { Currency } from 'app/entities/enumerations/currency.model';

@Component({
  selector: 'jhi-capture-update',
  templateUrl: './capture-update.component.html',
})
export class CaptureUpdateComponent implements OnInit {
  isSaving = false;
  captureStatusValues = Object.keys(CaptureStatus);
  currencyValues = Object.keys(Currency);

  editForm = this.fb.group({
    id: [],
    reference: [],
    createDateTimeUtc: [],
    status: [],
    amountToCapture: [],
    convertedAmountToCapture: [],
    convertedCurrency: [],
    conversionRate: [],
    isFinalCapture: [],
  });

  constructor(protected captureService: CaptureService, protected activatedRoute: ActivatedRoute, protected fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ capture }) => {
      if (capture.id === undefined) {
        const today = dayjs().startOf('day');
        capture.createDateTimeUtc = today;
      }

      this.updateForm(capture);
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const capture = this.createFromForm();
    if (capture.id !== undefined) {
      this.subscribeToSaveResponse(this.captureService.update(capture));
    } else {
      this.subscribeToSaveResponse(this.captureService.create(capture));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ICapture>>): void {
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

  protected updateForm(capture: ICapture): void {
    this.editForm.patchValue({
      id: capture.id,
      reference: capture.reference,
      createDateTimeUtc: capture.createDateTimeUtc ? capture.createDateTimeUtc.format(DATE_TIME_FORMAT) : null,
      status: capture.status,
      amountToCapture: capture.amountToCapture,
      convertedAmountToCapture: capture.convertedAmountToCapture,
      convertedCurrency: capture.convertedCurrency,
      conversionRate: capture.conversionRate,
      isFinalCapture: capture.isFinalCapture,
    });
  }

  protected createFromForm(): ICapture {
    return {
      ...new Capture(),
      id: this.editForm.get(['id'])!.value,
      reference: this.editForm.get(['reference'])!.value,
      createDateTimeUtc: this.editForm.get(['createDateTimeUtc'])!.value
        ? dayjs(this.editForm.get(['createDateTimeUtc'])!.value, DATE_TIME_FORMAT)
        : undefined,
      status: this.editForm.get(['status'])!.value,
      amountToCapture: this.editForm.get(['amountToCapture'])!.value,
      convertedAmountToCapture: this.editForm.get(['convertedAmountToCapture'])!.value,
      convertedCurrency: this.editForm.get(['convertedCurrency'])!.value,
      conversionRate: this.editForm.get(['conversionRate'])!.value,
      isFinalCapture: this.editForm.get(['isFinalCapture'])!.value,
    };
  }
}
