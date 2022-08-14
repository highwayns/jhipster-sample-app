import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { IAdminControlsMethods, AdminControlsMethods } from '../admin-controls-methods.model';
import { AdminControlsMethodsService } from '../service/admin-controls-methods.service';
import { IAdminControls } from 'app/entities/admin-controls/admin-controls.model';
import { AdminControlsService } from 'app/entities/admin-controls/service/admin-controls.service';

@Component({
  selector: 'jhi-admin-controls-methods-update',
  templateUrl: './admin-controls-methods-update.component.html',
})
export class AdminControlsMethodsUpdateComponent implements OnInit {
  isSaving = false;

  adminControlsSharedCollection: IAdminControls[] = [];

  editForm = this.fb.group({
    id: [],
    method: [null, [Validators.maxLength(100)]],
    argument: [null, [Validators.required, Validators.maxLength(255)]],
    order: [null, [Validators.required]],
    pId: [null, [Validators.required]],
    controlId: [],
  });

  constructor(
    protected adminControlsMethodsService: AdminControlsMethodsService,
    protected adminControlsService: AdminControlsService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ adminControlsMethods }) => {
      this.updateForm(adminControlsMethods);

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const adminControlsMethods = this.createFromForm();
    if (adminControlsMethods.id !== undefined) {
      this.subscribeToSaveResponse(this.adminControlsMethodsService.update(adminControlsMethods));
    } else {
      this.subscribeToSaveResponse(this.adminControlsMethodsService.create(adminControlsMethods));
    }
  }

  trackAdminControlsById(_index: number, item: IAdminControls): number {
    return item.id!;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IAdminControlsMethods>>): void {
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

  protected updateForm(adminControlsMethods: IAdminControlsMethods): void {
    this.editForm.patchValue({
      id: adminControlsMethods.id,
      method: adminControlsMethods.method,
      argument: adminControlsMethods.argument,
      order: adminControlsMethods.order,
      pId: adminControlsMethods.pId,
      controlId: adminControlsMethods.controlId,
    });

    this.adminControlsSharedCollection = this.adminControlsService.addAdminControlsToCollectionIfMissing(
      this.adminControlsSharedCollection,
      adminControlsMethods.controlId
    );
  }

  protected loadRelationshipsOptions(): void {
    this.adminControlsService
      .query()
      .pipe(map((res: HttpResponse<IAdminControls[]>) => res.body ?? []))
      .pipe(
        map((adminControls: IAdminControls[]) =>
          this.adminControlsService.addAdminControlsToCollectionIfMissing(adminControls, this.editForm.get('controlId')!.value)
        )
      )
      .subscribe((adminControls: IAdminControls[]) => (this.adminControlsSharedCollection = adminControls));
  }

  protected createFromForm(): IAdminControlsMethods {
    return {
      ...new AdminControlsMethods(),
      id: this.editForm.get(['id'])!.value,
      method: this.editForm.get(['method'])!.value,
      argument: this.editForm.get(['argument'])!.value,
      order: this.editForm.get(['order'])!.value,
      pId: this.editForm.get(['pId'])!.value,
      controlId: this.editForm.get(['controlId'])!.value,
    };
  }
}
