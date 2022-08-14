import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { IRequestStatus, RequestStatus } from '../request-status.model';
import { RequestStatusService } from '../service/request-status.service';

@Component({
  selector: 'jhi-request-status-update',
  templateUrl: './request-status-update.component.html',
})
export class RequestStatusUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    nameEn: [null, [Validators.required, Validators.maxLength(255)]],
    nameEs: [null, [Validators.required, Validators.maxLength(255)]],
    nameRu: [null, [Validators.required, Validators.maxLength(255)]],
    nameZh: [null, [Validators.required, Validators.maxLength(255)]],
  });

  constructor(protected requestStatusService: RequestStatusService, protected activatedRoute: ActivatedRoute, protected fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ requestStatus }) => {
      this.updateForm(requestStatus);
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const requestStatus = this.createFromForm();
    if (requestStatus.id !== undefined) {
      this.subscribeToSaveResponse(this.requestStatusService.update(requestStatus));
    } else {
      this.subscribeToSaveResponse(this.requestStatusService.create(requestStatus));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IRequestStatus>>): void {
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

  protected updateForm(requestStatus: IRequestStatus): void {
    this.editForm.patchValue({
      id: requestStatus.id,
      nameEn: requestStatus.nameEn,
      nameEs: requestStatus.nameEs,
      nameRu: requestStatus.nameRu,
      nameZh: requestStatus.nameZh,
    });
  }

  protected createFromForm(): IRequestStatus {
    return {
      ...new RequestStatus(),
      id: this.editForm.get(['id'])!.value,
      nameEn: this.editForm.get(['nameEn'])!.value,
      nameEs: this.editForm.get(['nameEs'])!.value,
      nameRu: this.editForm.get(['nameRu'])!.value,
      nameZh: this.editForm.get(['nameZh'])!.value,
    };
  }
}
