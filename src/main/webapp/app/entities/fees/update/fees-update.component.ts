import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import dayjs from 'dayjs/esm';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';

import { IFees, Fees } from '../fees.model';
import { FeesService } from '../service/fees.service';

@Component({
  selector: 'jhi-fees-update',
  templateUrl: './fees-update.component.html',
})
export class FeesUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    fee: [null, [Validators.required]],
    date: [null, [Validators.required]],
  });

  constructor(protected feesService: FeesService, protected activatedRoute: ActivatedRoute, protected fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ fees }) => {
      if (fees.id === undefined) {
        const today = dayjs().startOf('day');
        fees.date = today;
      }

      this.updateForm(fees);
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const fees = this.createFromForm();
    if (fees.id !== undefined) {
      this.subscribeToSaveResponse(this.feesService.update(fees));
    } else {
      this.subscribeToSaveResponse(this.feesService.create(fees));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IFees>>): void {
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

  protected updateForm(fees: IFees): void {
    this.editForm.patchValue({
      id: fees.id,
      fee: fees.fee,
      date: fees.date ? fees.date.format(DATE_TIME_FORMAT) : null,
    });
  }

  protected createFromForm(): IFees {
    return {
      ...new Fees(),
      id: this.editForm.get(['id'])!.value,
      fee: this.editForm.get(['fee'])!.value,
      date: this.editForm.get(['date'])!.value ? dayjs(this.editForm.get(['date'])!.value, DATE_TIME_FORMAT) : undefined,
    };
  }
}
