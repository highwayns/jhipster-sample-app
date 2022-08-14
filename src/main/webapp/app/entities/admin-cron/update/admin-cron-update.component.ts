import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { IAdminCron, AdminCron } from '../admin-cron.model';
import { AdminCronService } from '../service/admin-cron.service';
import { IAdminControls } from 'app/entities/admin-controls/admin-controls.model';
import { AdminControlsService } from 'app/entities/admin-controls/service/admin-controls.service';
import { IAdminControlsMethods } from 'app/entities/admin-controls-methods/admin-controls-methods.model';
import { AdminControlsMethodsService } from 'app/entities/admin-controls-methods/service/admin-controls-methods.service';

@Component({
  selector: 'jhi-admin-cron-update',
  templateUrl: './admin-cron-update.component.html',
})
export class AdminCronUpdateComponent implements OnInit {
  isSaving = false;

  adminControlsSharedCollection: IAdminControls[] = [];
  adminControlsMethodsSharedCollection: IAdminControlsMethods[] = [];

  editForm = this.fb.group({
    id: [],
    day: [null, [Validators.required, Validators.maxLength(30)]],
    month: [null, [Validators.required, Validators.maxLength(30)]],
    year: [null, [Validators.required, Validators.maxLength(30)]],
    sendCondition: [null, [Validators.required, Validators.maxLength(255)]],
    controlId: [],
    methodId: [],
  });

  constructor(
    protected adminCronService: AdminCronService,
    protected adminControlsService: AdminControlsService,
    protected adminControlsMethodsService: AdminControlsMethodsService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ adminCron }) => {
      this.updateForm(adminCron);

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const adminCron = this.createFromForm();
    if (adminCron.id !== undefined) {
      this.subscribeToSaveResponse(this.adminCronService.update(adminCron));
    } else {
      this.subscribeToSaveResponse(this.adminCronService.create(adminCron));
    }
  }

  trackAdminControlsById(_index: number, item: IAdminControls): number {
    return item.id!;
  }

  trackAdminControlsMethodsById(_index: number, item: IAdminControlsMethods): number {
    return item.id!;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IAdminCron>>): void {
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

  protected updateForm(adminCron: IAdminCron): void {
    this.editForm.patchValue({
      id: adminCron.id,
      day: adminCron.day,
      month: adminCron.month,
      year: adminCron.year,
      sendCondition: adminCron.sendCondition,
      controlId: adminCron.controlId,
      methodId: adminCron.methodId,
    });

    this.adminControlsSharedCollection = this.adminControlsService.addAdminControlsToCollectionIfMissing(
      this.adminControlsSharedCollection,
      adminCron.controlId
    );
    this.adminControlsMethodsSharedCollection = this.adminControlsMethodsService.addAdminControlsMethodsToCollectionIfMissing(
      this.adminControlsMethodsSharedCollection,
      adminCron.methodId
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

    this.adminControlsMethodsService
      .query()
      .pipe(map((res: HttpResponse<IAdminControlsMethods[]>) => res.body ?? []))
      .pipe(
        map((adminControlsMethods: IAdminControlsMethods[]) =>
          this.adminControlsMethodsService.addAdminControlsMethodsToCollectionIfMissing(
            adminControlsMethods,
            this.editForm.get('methodId')!.value
          )
        )
      )
      .subscribe((adminControlsMethods: IAdminControlsMethods[]) => (this.adminControlsMethodsSharedCollection = adminControlsMethods));
  }

  protected createFromForm(): IAdminCron {
    return {
      ...new AdminCron(),
      id: this.editForm.get(['id'])!.value,
      day: this.editForm.get(['day'])!.value,
      month: this.editForm.get(['month'])!.value,
      year: this.editForm.get(['year'])!.value,
      sendCondition: this.editForm.get(['sendCondition'])!.value,
      controlId: this.editForm.get(['controlId'])!.value,
      methodId: this.editForm.get(['methodId'])!.value,
    };
  }
}
