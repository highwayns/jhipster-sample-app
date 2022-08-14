import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { IAdminGroups, AdminGroups } from '../admin-groups.model';
import { AdminGroupsService } from '../service/admin-groups.service';

@Component({
  selector: 'jhi-admin-groups-update',
  templateUrl: './admin-groups-update.component.html',
})
export class AdminGroupsUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    name: [null, [Validators.required, Validators.maxLength(50)]],
    order: [null, [Validators.required]],
  });

  constructor(protected adminGroupsService: AdminGroupsService, protected activatedRoute: ActivatedRoute, protected fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ adminGroups }) => {
      this.updateForm(adminGroups);
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const adminGroups = this.createFromForm();
    if (adminGroups.id !== undefined) {
      this.subscribeToSaveResponse(this.adminGroupsService.update(adminGroups));
    } else {
      this.subscribeToSaveResponse(this.adminGroupsService.create(adminGroups));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IAdminGroups>>): void {
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

  protected updateForm(adminGroups: IAdminGroups): void {
    this.editForm.patchValue({
      id: adminGroups.id,
      name: adminGroups.name,
      order: adminGroups.order,
    });
  }

  protected createFromForm(): IAdminGroups {
    return {
      ...new AdminGroups(),
      id: this.editForm.get(['id'])!.value,
      name: this.editForm.get(['name'])!.value,
      order: this.editForm.get(['order'])!.value,
    };
  }
}
