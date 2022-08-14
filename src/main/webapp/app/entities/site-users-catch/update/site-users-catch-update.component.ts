import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { ISiteUsersCatch, SiteUsersCatch } from '../site-users-catch.model';
import { SiteUsersCatchService } from '../service/site-users-catch.service';
import { ISiteUsers } from 'app/entities/site-users/site-users.model';
import { SiteUsersService } from 'app/entities/site-users/service/site-users.service';

@Component({
  selector: 'jhi-site-users-catch-update',
  templateUrl: './site-users-catch-update.component.html',
})
export class SiteUsersCatchUpdateComponent implements OnInit {
  isSaving = false;

  siteUsersCollection: ISiteUsers[] = [];

  editForm = this.fb.group({
    id: [],
    attempts: [null, [Validators.required]],
    siteUser: [],
  });

  constructor(
    protected siteUsersCatchService: SiteUsersCatchService,
    protected siteUsersService: SiteUsersService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ siteUsersCatch }) => {
      this.updateForm(siteUsersCatch);

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const siteUsersCatch = this.createFromForm();
    if (siteUsersCatch.id !== undefined) {
      this.subscribeToSaveResponse(this.siteUsersCatchService.update(siteUsersCatch));
    } else {
      this.subscribeToSaveResponse(this.siteUsersCatchService.create(siteUsersCatch));
    }
  }

  trackSiteUsersById(_index: number, item: ISiteUsers): number {
    return item.id!;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ISiteUsersCatch>>): void {
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

  protected updateForm(siteUsersCatch: ISiteUsersCatch): void {
    this.editForm.patchValue({
      id: siteUsersCatch.id,
      attempts: siteUsersCatch.attempts,
      siteUser: siteUsersCatch.siteUser,
    });

    this.siteUsersCollection = this.siteUsersService.addSiteUsersToCollectionIfMissing(this.siteUsersCollection, siteUsersCatch.siteUser);
  }

  protected loadRelationshipsOptions(): void {
    this.siteUsersService
      .query({ filter: 'siteuserscatch-is-null' })
      .pipe(map((res: HttpResponse<ISiteUsers[]>) => res.body ?? []))
      .pipe(
        map((siteUsers: ISiteUsers[]) =>
          this.siteUsersService.addSiteUsersToCollectionIfMissing(siteUsers, this.editForm.get('siteUser')!.value)
        )
      )
      .subscribe((siteUsers: ISiteUsers[]) => (this.siteUsersCollection = siteUsers));
  }

  protected createFromForm(): ISiteUsersCatch {
    return {
      ...new SiteUsersCatch(),
      id: this.editForm.get(['id'])!.value,
      attempts: this.editForm.get(['attempts'])!.value,
      siteUser: this.editForm.get(['siteUser'])!.value,
    };
  }
}
