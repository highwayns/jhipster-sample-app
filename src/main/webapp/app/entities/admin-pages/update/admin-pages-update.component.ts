import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { IAdminPages, AdminPages } from '../admin-pages.model';
import { AdminPagesService } from '../service/admin-pages.service';
import { YesNo } from 'app/entities/enumerations/yes-no.model';

@Component({
  selector: 'jhi-admin-pages-update',
  templateUrl: './admin-pages-update.component.html',
})
export class AdminPagesUpdateComponent implements OnInit {
  isSaving = false;
  yesNoValues = Object.keys(YesNo);

  editForm = this.fb.group({
    id: [],
    fId: [null, [Validators.required]],
    name: [null, [Validators.required, Validators.maxLength(255)]],
    url: [null, [Validators.required, Validators.maxLength(255)]],
    icon: [null, [Validators.required, Validators.maxLength(255)]],
    order: [null, [Validators.required]],
    pageMapReorders: [null, [Validators.required]],
    oneRecord: [null, [Validators.required]],
  });

  constructor(protected adminPagesService: AdminPagesService, protected activatedRoute: ActivatedRoute, protected fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ adminPages }) => {
      this.updateForm(adminPages);
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const adminPages = this.createFromForm();
    if (adminPages.id !== undefined) {
      this.subscribeToSaveResponse(this.adminPagesService.update(adminPages));
    } else {
      this.subscribeToSaveResponse(this.adminPagesService.create(adminPages));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IAdminPages>>): void {
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

  protected updateForm(adminPages: IAdminPages): void {
    this.editForm.patchValue({
      id: adminPages.id,
      fId: adminPages.fId,
      name: adminPages.name,
      url: adminPages.url,
      icon: adminPages.icon,
      order: adminPages.order,
      pageMapReorders: adminPages.pageMapReorders,
      oneRecord: adminPages.oneRecord,
    });
  }

  protected createFromForm(): IAdminPages {
    return {
      ...new AdminPages(),
      id: this.editForm.get(['id'])!.value,
      fId: this.editForm.get(['fId'])!.value,
      name: this.editForm.get(['name'])!.value,
      url: this.editForm.get(['url'])!.value,
      icon: this.editForm.get(['icon'])!.value,
      order: this.editForm.get(['order'])!.value,
      pageMapReorders: this.editForm.get(['pageMapReorders'])!.value,
      oneRecord: this.editForm.get(['oneRecord'])!.value,
    };
  }
}
