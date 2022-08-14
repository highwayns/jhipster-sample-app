import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { IRequestDescriptions, RequestDescriptions } from '../request-descriptions.model';
import { RequestDescriptionsService } from '../service/request-descriptions.service';

@Component({
  selector: 'jhi-request-descriptions-update',
  templateUrl: './request-descriptions-update.component.html',
})
export class RequestDescriptionsUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    nameEn: [null, [Validators.required, Validators.maxLength(255)]],
    nameEs: [null, [Validators.required, Validators.maxLength(255)]],
    nameRu: [null, [Validators.required, Validators.maxLength(255)]],
    nameZh: [null, [Validators.required, Validators.maxLength(255)]],
  });

  constructor(
    protected requestDescriptionsService: RequestDescriptionsService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ requestDescriptions }) => {
      this.updateForm(requestDescriptions);
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const requestDescriptions = this.createFromForm();
    if (requestDescriptions.id !== undefined) {
      this.subscribeToSaveResponse(this.requestDescriptionsService.update(requestDescriptions));
    } else {
      this.subscribeToSaveResponse(this.requestDescriptionsService.create(requestDescriptions));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IRequestDescriptions>>): void {
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

  protected updateForm(requestDescriptions: IRequestDescriptions): void {
    this.editForm.patchValue({
      id: requestDescriptions.id,
      nameEn: requestDescriptions.nameEn,
      nameEs: requestDescriptions.nameEs,
      nameRu: requestDescriptions.nameRu,
      nameZh: requestDescriptions.nameZh,
    });
  }

  protected createFromForm(): IRequestDescriptions {
    return {
      ...new RequestDescriptions(),
      id: this.editForm.get(['id'])!.value,
      nameEn: this.editForm.get(['nameEn'])!.value,
      nameEs: this.editForm.get(['nameEs'])!.value,
      nameRu: this.editForm.get(['nameRu'])!.value,
      nameZh: this.editForm.get(['nameZh'])!.value,
    };
  }
}
