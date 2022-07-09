import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import dayjs from 'dayjs/esm';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';

import { IRefundStep, RefundStep } from '../refund-step.model';
import { RefundStepService } from '../service/refund-step.service';
import { IResultAttributes } from 'app/entities/result-attributes/result-attributes.model';
import { ResultAttributesService } from 'app/entities/result-attributes/service/result-attributes.service';
import { RefundStepAction } from 'app/entities/enumerations/refund-step-action.model';
import { RefundStatus } from 'app/entities/enumerations/refund-status.model';

@Component({
  selector: 'jhi-refund-step-update',
  templateUrl: './refund-step-update.component.html',
})
export class RefundStepUpdateComponent implements OnInit {
  isSaving = false;
  refundStepActionValues = Object.keys(RefundStepAction);
  refundStatusValues = Object.keys(RefundStatus);

  resultAttributesCollection: IResultAttributes[] = [];

  editForm = this.fb.group({
    id: [],
    reference: [],
    createDateTimeUtc: [],
    action: [],
    status: [],
    description: [],
    resultAttributes: [],
  });

  constructor(
    protected refundStepService: RefundStepService,
    protected resultAttributesService: ResultAttributesService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ refundStep }) => {
      if (refundStep.id === undefined) {
        const today = dayjs().startOf('day');
        refundStep.createDateTimeUtc = today;
      }

      this.updateForm(refundStep);

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const refundStep = this.createFromForm();
    if (refundStep.id !== undefined) {
      this.subscribeToSaveResponse(this.refundStepService.update(refundStep));
    } else {
      this.subscribeToSaveResponse(this.refundStepService.create(refundStep));
    }
  }

  trackResultAttributesById(_index: number, item: IResultAttributes): number {
    return item.id!;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IRefundStep>>): void {
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

  protected updateForm(refundStep: IRefundStep): void {
    this.editForm.patchValue({
      id: refundStep.id,
      reference: refundStep.reference,
      createDateTimeUtc: refundStep.createDateTimeUtc ? refundStep.createDateTimeUtc.format(DATE_TIME_FORMAT) : null,
      action: refundStep.action,
      status: refundStep.status,
      description: refundStep.description,
      resultAttributes: refundStep.resultAttributes,
    });

    this.resultAttributesCollection = this.resultAttributesService.addResultAttributesToCollectionIfMissing(
      this.resultAttributesCollection,
      refundStep.resultAttributes
    );
  }

  protected loadRelationshipsOptions(): void {
    this.resultAttributesService
      .query({ filter: 'refundstep-is-null' })
      .pipe(map((res: HttpResponse<IResultAttributes[]>) => res.body ?? []))
      .pipe(
        map((resultAttributes: IResultAttributes[]) =>
          this.resultAttributesService.addResultAttributesToCollectionIfMissing(
            resultAttributes,
            this.editForm.get('resultAttributes')!.value
          )
        )
      )
      .subscribe((resultAttributes: IResultAttributes[]) => (this.resultAttributesCollection = resultAttributes));
  }

  protected createFromForm(): IRefundStep {
    return {
      ...new RefundStep(),
      id: this.editForm.get(['id'])!.value,
      reference: this.editForm.get(['reference'])!.value,
      createDateTimeUtc: this.editForm.get(['createDateTimeUtc'])!.value
        ? dayjs(this.editForm.get(['createDateTimeUtc'])!.value, DATE_TIME_FORMAT)
        : undefined,
      action: this.editForm.get(['action'])!.value,
      status: this.editForm.get(['status'])!.value,
      description: this.editForm.get(['description'])!.value,
      resultAttributes: this.editForm.get(['resultAttributes'])!.value,
    };
  }
}
