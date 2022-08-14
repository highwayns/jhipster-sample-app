import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { IRequestTypes, RequestTypes } from '../request-types.model';
import { RequestTypesService } from '../service/request-types.service';

@Component({
  selector: 'jhi-request-types-update',
  templateUrl: './request-types-update.component.html',
})
export class RequestTypesUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    nameEn: [null, [Validators.required, Validators.maxLength(255)]],
    nameEs: [null, [Validators.required, Validators.maxLength(255)]],
    nameRu: [null, [Validators.required, Validators.maxLength(255)]],
    nameZh: [null, [Validators.required, Validators.maxLength(255)]],
  });

  constructor(protected requestTypesService: RequestTypesService, protected activatedRoute: ActivatedRoute, protected fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ requestTypes }) => {
      this.updateForm(requestTypes);
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const requestTypes = this.createFromForm();
    if (requestTypes.id !== undefined) {
      this.subscribeToSaveResponse(this.requestTypesService.update(requestTypes));
    } else {
      this.subscribeToSaveResponse(this.requestTypesService.create(requestTypes));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IRequestTypes>>): void {
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

  protected updateForm(requestTypes: IRequestTypes): void {
    this.editForm.patchValue({
      id: requestTypes.id,
      nameEn: requestTypes.nameEn,
      nameEs: requestTypes.nameEs,
      nameRu: requestTypes.nameRu,
      nameZh: requestTypes.nameZh,
    });
  }

  protected createFromForm(): IRequestTypes {
    return {
      ...new RequestTypes(),
      id: this.editForm.get(['id'])!.value,
      nameEn: this.editForm.get(['nameEn'])!.value,
      nameEs: this.editForm.get(['nameEs'])!.value,
      nameRu: this.editForm.get(['nameRu'])!.value,
      nameZh: this.editForm.get(['nameZh'])!.value,
    };
  }
}
