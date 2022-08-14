import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { IHistoricalData, HistoricalData } from '../historical-data.model';
import { HistoricalDataService } from '../service/historical-data.service';

@Component({
  selector: 'jhi-historical-data-update',
  templateUrl: './historical-data-update.component.html',
})
export class HistoricalDataUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [null, [Validators.required]],
    date: [null, [Validators.required]],
    usd: [null, [Validators.required]],
  });

  constructor(
    protected historicalDataService: HistoricalDataService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ historicalData }) => {
      this.updateForm(historicalData);
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const historicalData = this.createFromForm();
    if (historicalData.id !== undefined) {
      this.subscribeToSaveResponse(this.historicalDataService.update(historicalData));
    } else {
      this.subscribeToSaveResponse(this.historicalDataService.create(historicalData));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IHistoricalData>>): void {
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

  protected updateForm(historicalData: IHistoricalData): void {
    this.editForm.patchValue({
      id: historicalData.id,
      date: historicalData.date,
      usd: historicalData.usd,
    });
  }

  protected createFromForm(): IHistoricalData {
    return {
      ...new HistoricalData(),
      id: this.editForm.get(['id'])!.value,
      date: this.editForm.get(['date'])!.value,
      usd: this.editForm.get(['usd'])!.value,
    };
  }
}
