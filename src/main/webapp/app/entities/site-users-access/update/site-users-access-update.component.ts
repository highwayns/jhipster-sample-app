import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { ISiteUsersAccess, SiteUsersAccess } from '../site-users-access.model';
import { SiteUsersAccessService } from '../service/site-users-access.service';
import { ISiteUsers } from 'app/entities/site-users/site-users.model';
import { SiteUsersService } from 'app/entities/site-users/service/site-users.service';

@Component({
  selector: 'jhi-site-users-access-update',
  templateUrl: './site-users-access-update.component.html',
})
export class SiteUsersAccessUpdateComponent implements OnInit {
  isSaving = false;

  siteUsersCollection: ISiteUsers[] = [];

  editForm = this.fb.group({
    id: [],
    start: [null, [Validators.required]],
    last: [null, [Validators.required]],
    attempts: [null, [Validators.required]],
    siteUser: [],
  });

  constructor(
    protected siteUsersAccessService: SiteUsersAccessService,
    protected siteUsersService: SiteUsersService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ siteUsersAccess }) => {
      this.updateForm(siteUsersAccess);

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const siteUsersAccess = this.createFromForm();
    if (siteUsersAccess.id !== undefined) {
      this.subscribeToSaveResponse(this.siteUsersAccessService.update(siteUsersAccess));
    } else {
      this.subscribeToSaveResponse(this.siteUsersAccessService.create(siteUsersAccess));
    }
  }

  trackSiteUsersById(_index: number, item: ISiteUsers): number {
    return item.id!;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ISiteUsersAccess>>): void {
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

  protected updateForm(siteUsersAccess: ISiteUsersAccess): void {
    this.editForm.patchValue({
      id: siteUsersAccess.id,
      start: siteUsersAccess.start,
      last: siteUsersAccess.last,
      attempts: siteUsersAccess.attempts,
      siteUser: siteUsersAccess.siteUser,
    });

    this.siteUsersCollection = this.siteUsersService.addSiteUsersToCollectionIfMissing(this.siteUsersCollection, siteUsersAccess.siteUser);
  }

  protected loadRelationshipsOptions(): void {
    this.siteUsersService
      .query({ filter: 'siteusersaccess-is-null' })
      .pipe(map((res: HttpResponse<ISiteUsers[]>) => res.body ?? []))
      .pipe(
        map((siteUsers: ISiteUsers[]) =>
          this.siteUsersService.addSiteUsersToCollectionIfMissing(siteUsers, this.editForm.get('siteUser')!.value)
        )
      )
      .subscribe((siteUsers: ISiteUsers[]) => (this.siteUsersCollection = siteUsers));
  }

  protected createFromForm(): ISiteUsersAccess {
    return {
      ...new SiteUsersAccess(),
      id: this.editForm.get(['id'])!.value,
      start: this.editForm.get(['start'])!.value,
      last: this.editForm.get(['last'])!.value,
      attempts: this.editForm.get(['attempts'])!.value,
      siteUser: this.editForm.get(['siteUser'])!.value,
    };
  }
}
