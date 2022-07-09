import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import dayjs from 'dayjs/esm';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';

import { IRecurrenceCriteria, RecurrenceCriteria } from '../recurrence-criteria.model';
import { RecurrenceCriteriaService } from '../service/recurrence-criteria.service';
import { RecurrenceType } from 'app/entities/enumerations/recurrence-type.model';

@Component({
  selector: 'jhi-recurrence-criteria-update',
  templateUrl: './recurrence-criteria-update.component.html',
})
export class RecurrenceCriteriaUpdateComponent implements OnInit {
  isSaving = false;
  recurrenceTypeValues = Object.keys(RecurrenceType);

  editForm = this.fb.group({
    id: [],
    recurrenceType: [],
    recurringExpiry: [],
    recurringFrequency: [],
    instalments: [],
  });

  constructor(
    protected recurrenceCriteriaService: RecurrenceCriteriaService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ recurrenceCriteria }) => {
      if (recurrenceCriteria.id === undefined) {
        const today = dayjs().startOf('day');
        recurrenceCriteria.recurringExpiry = today;
      }

      this.updateForm(recurrenceCriteria);
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const recurrenceCriteria = this.createFromForm();
    if (recurrenceCriteria.id !== undefined) {
      this.subscribeToSaveResponse(this.recurrenceCriteriaService.update(recurrenceCriteria));
    } else {
      this.subscribeToSaveResponse(this.recurrenceCriteriaService.create(recurrenceCriteria));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IRecurrenceCriteria>>): void {
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

  protected updateForm(recurrenceCriteria: IRecurrenceCriteria): void {
    this.editForm.patchValue({
      id: recurrenceCriteria.id,
      recurrenceType: recurrenceCriteria.recurrenceType,
      recurringExpiry: recurrenceCriteria.recurringExpiry ? recurrenceCriteria.recurringExpiry.format(DATE_TIME_FORMAT) : null,
      recurringFrequency: recurrenceCriteria.recurringFrequency,
      instalments: recurrenceCriteria.instalments,
    });
  }

  protected createFromForm(): IRecurrenceCriteria {
    return {
      ...new RecurrenceCriteria(),
      id: this.editForm.get(['id'])!.value,
      recurrenceType: this.editForm.get(['recurrenceType'])!.value,
      recurringExpiry: this.editForm.get(['recurringExpiry'])!.value
        ? dayjs(this.editForm.get(['recurringExpiry'])!.value, DATE_TIME_FORMAT)
        : undefined,
      recurringFrequency: this.editForm.get(['recurringFrequency'])!.value,
      instalments: this.editForm.get(['instalments'])!.value,
    };
  }
}
