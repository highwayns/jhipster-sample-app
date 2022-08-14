import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { ICurrentStats, CurrentStats } from '../current-stats.model';
import { CurrentStatsService } from '../service/current-stats.service';

@Component({
  selector: 'jhi-current-stats-update',
  templateUrl: './current-stats-update.component.html',
})
export class CurrentStatsUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    totalBtc: [null, [Validators.required]],
    marketCap: [null, [Validators.required]],
    tradeVolume: [null, [Validators.required]],
  });

  constructor(protected currentStatsService: CurrentStatsService, protected activatedRoute: ActivatedRoute, protected fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ currentStats }) => {
      this.updateForm(currentStats);
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const currentStats = this.createFromForm();
    if (currentStats.id !== undefined) {
      this.subscribeToSaveResponse(this.currentStatsService.update(currentStats));
    } else {
      this.subscribeToSaveResponse(this.currentStatsService.create(currentStats));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ICurrentStats>>): void {
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

  protected updateForm(currentStats: ICurrentStats): void {
    this.editForm.patchValue({
      id: currentStats.id,
      totalBtc: currentStats.totalBtc,
      marketCap: currentStats.marketCap,
      tradeVolume: currentStats.tradeVolume,
    });
  }

  protected createFromForm(): ICurrentStats {
    return {
      ...new CurrentStats(),
      id: this.editForm.get(['id'])!.value,
      totalBtc: this.editForm.get(['totalBtc'])!.value,
      marketCap: this.editForm.get(['marketCap'])!.value,
      tradeVolume: this.editForm.get(['tradeVolume'])!.value,
    };
  }
}
