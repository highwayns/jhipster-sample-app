import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { IAbuseTrigger, AbuseTrigger } from '../abuse-trigger.model';
import { AbuseTriggerService } from '../service/abuse-trigger.service';

@Component({
  selector: 'jhi-abuse-trigger-update',
  templateUrl: './abuse-trigger-update.component.html',
})
export class AbuseTriggerUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    score: [],
    code: [],
    description: [],
  });

  constructor(protected abuseTriggerService: AbuseTriggerService, protected activatedRoute: ActivatedRoute, protected fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ abuseTrigger }) => {
      this.updateForm(abuseTrigger);
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const abuseTrigger = this.createFromForm();
    if (abuseTrigger.id !== undefined) {
      this.subscribeToSaveResponse(this.abuseTriggerService.update(abuseTrigger));
    } else {
      this.subscribeToSaveResponse(this.abuseTriggerService.create(abuseTrigger));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IAbuseTrigger>>): void {
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

  protected updateForm(abuseTrigger: IAbuseTrigger): void {
    this.editForm.patchValue({
      id: abuseTrigger.id,
      score: abuseTrigger.score,
      code: abuseTrigger.code,
      description: abuseTrigger.description,
    });
  }

  protected createFromForm(): IAbuseTrigger {
    return {
      ...new AbuseTrigger(),
      id: this.editForm.get(['id'])!.value,
      score: this.editForm.get(['score'])!.value,
      code: this.editForm.get(['code'])!.value,
      description: this.editForm.get(['description'])!.value,
    };
  }
}
