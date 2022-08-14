import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { IAdminTabs, AdminTabs } from '../admin-tabs.model';
import { AdminTabsService } from '../service/admin-tabs.service';
import { YesNo } from 'app/entities/enumerations/yes-no.model';

@Component({
  selector: 'jhi-admin-tabs-update',
  templateUrl: './admin-tabs-update.component.html',
})
export class AdminTabsUpdateComponent implements OnInit {
  isSaving = false;
  yesNoValues = Object.keys(YesNo);

  editForm = this.fb.group({
    id: [],
    name: [null, [Validators.required, Validators.maxLength(255)]],
    order: [null, [Validators.required]],
    icon: [null, [Validators.required, Validators.maxLength(255)]],
    url: [null, [Validators.required, Validators.maxLength(255)]],
    hidden: [null, [Validators.required]],
    isCtrlPanel: [null, [Validators.required]],
    forGroup: [null, [Validators.required]],
    oneRecord: [null, [Validators.required]],
  });

  constructor(protected adminTabsService: AdminTabsService, protected activatedRoute: ActivatedRoute, protected fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ adminTabs }) => {
      this.updateForm(adminTabs);
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const adminTabs = this.createFromForm();
    if (adminTabs.id !== undefined) {
      this.subscribeToSaveResponse(this.adminTabsService.update(adminTabs));
    } else {
      this.subscribeToSaveResponse(this.adminTabsService.create(adminTabs));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IAdminTabs>>): void {
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

  protected updateForm(adminTabs: IAdminTabs): void {
    this.editForm.patchValue({
      id: adminTabs.id,
      name: adminTabs.name,
      order: adminTabs.order,
      icon: adminTabs.icon,
      url: adminTabs.url,
      hidden: adminTabs.hidden,
      isCtrlPanel: adminTabs.isCtrlPanel,
      forGroup: adminTabs.forGroup,
      oneRecord: adminTabs.oneRecord,
    });
  }

  protected createFromForm(): IAdminTabs {
    return {
      ...new AdminTabs(),
      id: this.editForm.get(['id'])!.value,
      name: this.editForm.get(['name'])!.value,
      order: this.editForm.get(['order'])!.value,
      icon: this.editForm.get(['icon'])!.value,
      url: this.editForm.get(['url'])!.value,
      hidden: this.editForm.get(['hidden'])!.value,
      isCtrlPanel: this.editForm.get(['isCtrlPanel'])!.value,
      forGroup: this.editForm.get(['forGroup'])!.value,
      oneRecord: this.editForm.get(['oneRecord'])!.value,
    };
  }
}
