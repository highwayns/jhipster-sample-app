import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { IAdminImageSizes, AdminImageSizes } from '../admin-image-sizes.model';
import { AdminImageSizesService } from '../service/admin-image-sizes.service';

@Component({
  selector: 'jhi-admin-image-sizes-update',
  templateUrl: './admin-image-sizes-update.component.html',
})
export class AdminImageSizesUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    fieldName: [null, [Validators.required, Validators.maxLength(100)]],
    value: [null, [Validators.required, Validators.maxLength(255)]],
  });

  constructor(
    protected adminImageSizesService: AdminImageSizesService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ adminImageSizes }) => {
      this.updateForm(adminImageSizes);
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const adminImageSizes = this.createFromForm();
    if (adminImageSizes.id !== undefined) {
      this.subscribeToSaveResponse(this.adminImageSizesService.update(adminImageSizes));
    } else {
      this.subscribeToSaveResponse(this.adminImageSizesService.create(adminImageSizes));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IAdminImageSizes>>): void {
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

  protected updateForm(adminImageSizes: IAdminImageSizes): void {
    this.editForm.patchValue({
      id: adminImageSizes.id,
      fieldName: adminImageSizes.fieldName,
      value: adminImageSizes.value,
    });
  }

  protected createFromForm(): IAdminImageSizes {
    return {
      ...new AdminImageSizes(),
      id: this.editForm.get(['id'])!.value,
      fieldName: this.editForm.get(['fieldName'])!.value,
      value: this.editForm.get(['value'])!.value,
    };
  }
}
