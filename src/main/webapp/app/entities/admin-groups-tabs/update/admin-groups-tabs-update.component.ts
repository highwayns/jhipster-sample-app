import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { IAdminGroupsTabs, AdminGroupsTabs } from '../admin-groups-tabs.model';
import { AdminGroupsTabsService } from '../service/admin-groups-tabs.service';
import { IAdminTabs } from 'app/entities/admin-tabs/admin-tabs.model';
import { AdminTabsService } from 'app/entities/admin-tabs/service/admin-tabs.service';
import { IAdminGroups } from 'app/entities/admin-groups/admin-groups.model';
import { AdminGroupsService } from 'app/entities/admin-groups/service/admin-groups.service';

@Component({
  selector: 'jhi-admin-groups-tabs-update',
  templateUrl: './admin-groups-tabs-update.component.html',
})
export class AdminGroupsTabsUpdateComponent implements OnInit {
  isSaving = false;

  tabIdsCollection: IAdminTabs[] = [];
  adminGroupsSharedCollection: IAdminGroups[] = [];

  editForm = this.fb.group({
    id: [],
    permission: [null, [Validators.required]],
    tabId: [],
    groupId: [],
  });

  constructor(
    protected adminGroupsTabsService: AdminGroupsTabsService,
    protected adminTabsService: AdminTabsService,
    protected adminGroupsService: AdminGroupsService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ adminGroupsTabs }) => {
      this.updateForm(adminGroupsTabs);

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const adminGroupsTabs = this.createFromForm();
    if (adminGroupsTabs.id !== undefined) {
      this.subscribeToSaveResponse(this.adminGroupsTabsService.update(adminGroupsTabs));
    } else {
      this.subscribeToSaveResponse(this.adminGroupsTabsService.create(adminGroupsTabs));
    }
  }

  trackAdminTabsById(_index: number, item: IAdminTabs): number {
    return item.id!;
  }

  trackAdminGroupsById(_index: number, item: IAdminGroups): number {
    return item.id!;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IAdminGroupsTabs>>): void {
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

  protected updateForm(adminGroupsTabs: IAdminGroupsTabs): void {
    this.editForm.patchValue({
      id: adminGroupsTabs.id,
      permission: adminGroupsTabs.permission,
      tabId: adminGroupsTabs.tabId,
      groupId: adminGroupsTabs.groupId,
    });

    this.tabIdsCollection = this.adminTabsService.addAdminTabsToCollectionIfMissing(this.tabIdsCollection, adminGroupsTabs.tabId);
    this.adminGroupsSharedCollection = this.adminGroupsService.addAdminGroupsToCollectionIfMissing(
      this.adminGroupsSharedCollection,
      adminGroupsTabs.groupId
    );
  }

  protected loadRelationshipsOptions(): void {
    this.adminTabsService
      .query({ filter: 'admingroupstabs-is-null' })
      .pipe(map((res: HttpResponse<IAdminTabs[]>) => res.body ?? []))
      .pipe(
        map((adminTabs: IAdminTabs[]) =>
          this.adminTabsService.addAdminTabsToCollectionIfMissing(adminTabs, this.editForm.get('tabId')!.value)
        )
      )
      .subscribe((adminTabs: IAdminTabs[]) => (this.tabIdsCollection = adminTabs));

    this.adminGroupsService
      .query()
      .pipe(map((res: HttpResponse<IAdminGroups[]>) => res.body ?? []))
      .pipe(
        map((adminGroups: IAdminGroups[]) =>
          this.adminGroupsService.addAdminGroupsToCollectionIfMissing(adminGroups, this.editForm.get('groupId')!.value)
        )
      )
      .subscribe((adminGroups: IAdminGroups[]) => (this.adminGroupsSharedCollection = adminGroups));
  }

  protected createFromForm(): IAdminGroupsTabs {
    return {
      ...new AdminGroupsTabs(),
      id: this.editForm.get(['id'])!.value,
      permission: this.editForm.get(['permission'])!.value,
      tabId: this.editForm.get(['tabId'])!.value,
      groupId: this.editForm.get(['groupId'])!.value,
    };
  }
}
