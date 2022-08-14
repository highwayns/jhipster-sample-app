import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { IAdminGroupsPages, AdminGroupsPages } from '../admin-groups-pages.model';
import { AdminGroupsPagesService } from '../service/admin-groups-pages.service';
import { IAdminPages } from 'app/entities/admin-pages/admin-pages.model';
import { AdminPagesService } from 'app/entities/admin-pages/service/admin-pages.service';
import { IAdminGroups } from 'app/entities/admin-groups/admin-groups.model';
import { AdminGroupsService } from 'app/entities/admin-groups/service/admin-groups.service';

@Component({
  selector: 'jhi-admin-groups-pages-update',
  templateUrl: './admin-groups-pages-update.component.html',
})
export class AdminGroupsPagesUpdateComponent implements OnInit {
  isSaving = false;

  pageIdsCollection: IAdminPages[] = [];
  adminGroupsSharedCollection: IAdminGroups[] = [];

  editForm = this.fb.group({
    id: [],
    permission: [null, [Validators.required]],
    pageId: [],
    groupId: [],
  });

  constructor(
    protected adminGroupsPagesService: AdminGroupsPagesService,
    protected adminPagesService: AdminPagesService,
    protected adminGroupsService: AdminGroupsService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ adminGroupsPages }) => {
      this.updateForm(adminGroupsPages);

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const adminGroupsPages = this.createFromForm();
    if (adminGroupsPages.id !== undefined) {
      this.subscribeToSaveResponse(this.adminGroupsPagesService.update(adminGroupsPages));
    } else {
      this.subscribeToSaveResponse(this.adminGroupsPagesService.create(adminGroupsPages));
    }
  }

  trackAdminPagesById(_index: number, item: IAdminPages): number {
    return item.id!;
  }

  trackAdminGroupsById(_index: number, item: IAdminGroups): number {
    return item.id!;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IAdminGroupsPages>>): void {
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

  protected updateForm(adminGroupsPages: IAdminGroupsPages): void {
    this.editForm.patchValue({
      id: adminGroupsPages.id,
      permission: adminGroupsPages.permission,
      pageId: adminGroupsPages.pageId,
      groupId: adminGroupsPages.groupId,
    });

    this.pageIdsCollection = this.adminPagesService.addAdminPagesToCollectionIfMissing(this.pageIdsCollection, adminGroupsPages.pageId);
    this.adminGroupsSharedCollection = this.adminGroupsService.addAdminGroupsToCollectionIfMissing(
      this.adminGroupsSharedCollection,
      adminGroupsPages.groupId
    );
  }

  protected loadRelationshipsOptions(): void {
    this.adminPagesService
      .query({ filter: 'admingroupspages-is-null' })
      .pipe(map((res: HttpResponse<IAdminPages[]>) => res.body ?? []))
      .pipe(
        map((adminPages: IAdminPages[]) =>
          this.adminPagesService.addAdminPagesToCollectionIfMissing(adminPages, this.editForm.get('pageId')!.value)
        )
      )
      .subscribe((adminPages: IAdminPages[]) => (this.pageIdsCollection = adminPages));

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

  protected createFromForm(): IAdminGroupsPages {
    return {
      ...new AdminGroupsPages(),
      id: this.editForm.get(['id'])!.value,
      permission: this.editForm.get(['permission'])!.value,
      pageId: this.editForm.get(['pageId'])!.value,
      groupId: this.editForm.get(['groupId'])!.value,
    };
  }
}
