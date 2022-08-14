import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { IHistoryActions, HistoryActions } from '../history-actions.model';
import { HistoryActionsService } from '../service/history-actions.service';

@Component({
  selector: 'jhi-history-actions-update',
  templateUrl: './history-actions-update.component.html',
})
export class HistoryActionsUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    nameEn: [null, [Validators.required, Validators.maxLength(255)]],
    nameEs: [null, [Validators.required, Validators.maxLength(255)]],
    nameRu: [null, [Validators.required, Validators.maxLength(255)]],
    nameZh: [null, [Validators.required, Validators.maxLength(255)]],
  });

  constructor(
    protected historyActionsService: HistoryActionsService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ historyActions }) => {
      this.updateForm(historyActions);
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const historyActions = this.createFromForm();
    if (historyActions.id !== undefined) {
      this.subscribeToSaveResponse(this.historyActionsService.update(historyActions));
    } else {
      this.subscribeToSaveResponse(this.historyActionsService.create(historyActions));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IHistoryActions>>): void {
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

  protected updateForm(historyActions: IHistoryActions): void {
    this.editForm.patchValue({
      id: historyActions.id,
      nameEn: historyActions.nameEn,
      nameEs: historyActions.nameEs,
      nameRu: historyActions.nameRu,
      nameZh: historyActions.nameZh,
    });
  }

  protected createFromForm(): IHistoryActions {
    return {
      ...new HistoryActions(),
      id: this.editForm.get(['id'])!.value,
      nameEn: this.editForm.get(['nameEn'])!.value,
      nameEs: this.editForm.get(['nameEs'])!.value,
      nameRu: this.editForm.get(['nameRu'])!.value,
      nameZh: this.editForm.get(['nameZh'])!.value,
    };
  }
}
