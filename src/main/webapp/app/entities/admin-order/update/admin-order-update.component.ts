import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { IAdminOrder, AdminOrder } from '../admin-order.model';
import { AdminOrderService } from '../service/admin-order.service';
import { IAdminControls } from 'app/entities/admin-controls/admin-controls.model';
import { AdminControlsService } from 'app/entities/admin-controls/service/admin-controls.service';
import { IAdminUsers } from 'app/entities/admin-users/admin-users.model';
import { AdminUsersService } from 'app/entities/admin-users/service/admin-users.service';

@Component({
  selector: 'jhi-admin-order-update',
  templateUrl: './admin-order-update.component.html',
})
export class AdminOrderUpdateComponent implements OnInit {
  isSaving = false;

  adminControlsSharedCollection: IAdminControls[] = [];
  adminUsersSharedCollection: IAdminUsers[] = [];

  editForm = this.fb.group({
    id: [],
    orderBy: [null, [Validators.required, Validators.maxLength(50)]],
    orderAsc: [null, [Validators.required]],
    controlId: [],
    userId: [],
  });

  constructor(
    protected adminOrderService: AdminOrderService,
    protected adminControlsService: AdminControlsService,
    protected adminUsersService: AdminUsersService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ adminOrder }) => {
      this.updateForm(adminOrder);

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const adminOrder = this.createFromForm();
    if (adminOrder.id !== undefined) {
      this.subscribeToSaveResponse(this.adminOrderService.update(adminOrder));
    } else {
      this.subscribeToSaveResponse(this.adminOrderService.create(adminOrder));
    }
  }

  trackAdminControlsById(_index: number, item: IAdminControls): number {
    return item.id!;
  }

  trackAdminUsersById(_index: number, item: IAdminUsers): number {
    return item.id!;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IAdminOrder>>): void {
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

  protected updateForm(adminOrder: IAdminOrder): void {
    this.editForm.patchValue({
      id: adminOrder.id,
      orderBy: adminOrder.orderBy,
      orderAsc: adminOrder.orderAsc,
      controlId: adminOrder.controlId,
      userId: adminOrder.userId,
    });

    this.adminControlsSharedCollection = this.adminControlsService.addAdminControlsToCollectionIfMissing(
      this.adminControlsSharedCollection,
      adminOrder.controlId
    );
    this.adminUsersSharedCollection = this.adminUsersService.addAdminUsersToCollectionIfMissing(
      this.adminUsersSharedCollection,
      adminOrder.userId
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

    this.adminUsersService
      .query()
      .pipe(map((res: HttpResponse<IAdminUsers[]>) => res.body ?? []))
      .pipe(
        map((adminUsers: IAdminUsers[]) =>
          this.adminUsersService.addAdminUsersToCollectionIfMissing(adminUsers, this.editForm.get('userId')!.value)
        )
      )
      .subscribe((adminUsers: IAdminUsers[]) => (this.adminUsersSharedCollection = adminUsers));
  }

  protected createFromForm(): IAdminOrder {
    return {
      ...new AdminOrder(),
      id: this.editForm.get(['id'])!.value,
      orderBy: this.editForm.get(['orderBy'])!.value,
      orderAsc: this.editForm.get(['orderAsc'])!.value,
      controlId: this.editForm.get(['controlId'])!.value,
      userId: this.editForm.get(['userId'])!.value,
    };
  }
}
