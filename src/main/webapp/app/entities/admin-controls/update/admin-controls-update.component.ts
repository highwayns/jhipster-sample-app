import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { IAdminControls, AdminControls } from '../admin-controls.model';
import { AdminControlsService } from '../service/admin-controls.service';
import { IAdminPages } from 'app/entities/admin-pages/admin-pages.model';
import { AdminPagesService } from 'app/entities/admin-pages/service/admin-pages.service';
import { IAdminTabs } from 'app/entities/admin-tabs/admin-tabs.model';
import { AdminTabsService } from 'app/entities/admin-tabs/service/admin-tabs.service';
import { YesNo } from 'app/entities/enumerations/yes-no.model';

@Component({
  selector: 'jhi-admin-controls-update',
  templateUrl: './admin-controls-update.component.html',
})
export class AdminControlsUpdateComponent implements OnInit {
  isSaving = false;
  yesNoValues = Object.keys(YesNo);

  pageIdsCollection: IAdminPages[] = [];
  tabIdsCollection: IAdminTabs[] = [];

  editForm = this.fb.group({
    id: [],
    action: [null, [Validators.required, Validators.maxLength(50)]],
    controlClass: [null, [Validators.maxLength(50)]],
    argument: [null, [Validators.required, Validators.maxLength(255)]],
    order: [null, [Validators.required]],
    isStatic: [null, [Validators.required]],
    pageId: [],
    tabId: [],
  });

  constructor(
    protected adminControlsService: AdminControlsService,
    protected adminPagesService: AdminPagesService,
    protected adminTabsService: AdminTabsService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ adminControls }) => {
      this.updateForm(adminControls);

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const adminControls = this.createFromForm();
    if (adminControls.id !== undefined) {
      this.subscribeToSaveResponse(this.adminControlsService.update(adminControls));
    } else {
      this.subscribeToSaveResponse(this.adminControlsService.create(adminControls));
    }
  }

  trackAdminPagesById(_index: number, item: IAdminPages): number {
    return item.id!;
  }

  trackAdminTabsById(_index: number, item: IAdminTabs): number {
    return item.id!;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IAdminControls>>): void {
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

  protected updateForm(adminControls: IAdminControls): void {
    this.editForm.patchValue({
      id: adminControls.id,
      action: adminControls.action,
      controlClass: adminControls.controlClass,
      argument: adminControls.argument,
      order: adminControls.order,
      isStatic: adminControls.isStatic,
      pageId: adminControls.pageId,
      tabId: adminControls.tabId,
    });

    this.pageIdsCollection = this.adminPagesService.addAdminPagesToCollectionIfMissing(this.pageIdsCollection, adminControls.pageId);
    this.tabIdsCollection = this.adminTabsService.addAdminTabsToCollectionIfMissing(this.tabIdsCollection, adminControls.tabId);
  }

  protected loadRelationshipsOptions(): void {
    this.adminPagesService
      .query({ filter: 'admincontrols-is-null' })
      .pipe(map((res: HttpResponse<IAdminPages[]>) => res.body ?? []))
      .pipe(
        map((adminPages: IAdminPages[]) =>
          this.adminPagesService.addAdminPagesToCollectionIfMissing(adminPages, this.editForm.get('pageId')!.value)
        )
      )
      .subscribe((adminPages: IAdminPages[]) => (this.pageIdsCollection = adminPages));

    this.adminTabsService
      .query({ filter: 'admincontrols-is-null' })
      .pipe(map((res: HttpResponse<IAdminTabs[]>) => res.body ?? []))
      .pipe(
        map((adminTabs: IAdminTabs[]) =>
          this.adminTabsService.addAdminTabsToCollectionIfMissing(adminTabs, this.editForm.get('tabId')!.value)
        )
      )
      .subscribe((adminTabs: IAdminTabs[]) => (this.tabIdsCollection = adminTabs));
  }

  protected createFromForm(): IAdminControls {
    return {
      ...new AdminControls(),
      id: this.editForm.get(['id'])!.value,
      action: this.editForm.get(['action'])!.value,
      controlClass: this.editForm.get(['controlClass'])!.value,
      argument: this.editForm.get(['argument'])!.value,
      order: this.editForm.get(['order'])!.value,
      isStatic: this.editForm.get(['isStatic'])!.value,
      pageId: this.editForm.get(['pageId'])!.value,
      tabId: this.editForm.get(['tabId'])!.value,
    };
  }
}
