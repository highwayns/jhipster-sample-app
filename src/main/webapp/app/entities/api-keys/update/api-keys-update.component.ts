import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { IApiKeys, ApiKeys } from '../api-keys.model';
import { ApiKeysService } from '../service/api-keys.service';
import { ISiteUsers } from 'app/entities/site-users/site-users.model';
import { SiteUsersService } from 'app/entities/site-users/service/site-users.service';
import { YesNo } from 'app/entities/enumerations/yes-no.model';

@Component({
  selector: 'jhi-api-keys-update',
  templateUrl: './api-keys-update.component.html',
})
export class ApiKeysUpdateComponent implements OnInit {
  isSaving = false;
  yesNoValues = Object.keys(YesNo);

  siteUsersCollection: ISiteUsers[] = [];

  editForm = this.fb.group({
    id: [],
    key: [null, [Validators.required, Validators.maxLength(255)]],
    secret: [null, [Validators.required, Validators.maxLength(255)]],
    view: [null, [Validators.required]],
    orders: [null, [Validators.required]],
    withdraw: [null, [Validators.required]],
    nonce: [null, [Validators.required]],
    siteUser: [],
  });

  constructor(
    protected apiKeysService: ApiKeysService,
    protected siteUsersService: SiteUsersService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ apiKeys }) => {
      this.updateForm(apiKeys);

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const apiKeys = this.createFromForm();
    if (apiKeys.id !== undefined) {
      this.subscribeToSaveResponse(this.apiKeysService.update(apiKeys));
    } else {
      this.subscribeToSaveResponse(this.apiKeysService.create(apiKeys));
    }
  }

  trackSiteUsersById(_index: number, item: ISiteUsers): number {
    return item.id!;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IApiKeys>>): void {
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

  protected updateForm(apiKeys: IApiKeys): void {
    this.editForm.patchValue({
      id: apiKeys.id,
      key: apiKeys.key,
      secret: apiKeys.secret,
      view: apiKeys.view,
      orders: apiKeys.orders,
      withdraw: apiKeys.withdraw,
      nonce: apiKeys.nonce,
      siteUser: apiKeys.siteUser,
    });

    this.siteUsersCollection = this.siteUsersService.addSiteUsersToCollectionIfMissing(this.siteUsersCollection, apiKeys.siteUser);
  }

  protected loadRelationshipsOptions(): void {
    this.siteUsersService
      .query({ filter: 'apikeys-is-null' })
      .pipe(map((res: HttpResponse<ISiteUsers[]>) => res.body ?? []))
      .pipe(
        map((siteUsers: ISiteUsers[]) =>
          this.siteUsersService.addSiteUsersToCollectionIfMissing(siteUsers, this.editForm.get('siteUser')!.value)
        )
      )
      .subscribe((siteUsers: ISiteUsers[]) => (this.siteUsersCollection = siteUsers));
  }

  protected createFromForm(): IApiKeys {
    return {
      ...new ApiKeys(),
      id: this.editForm.get(['id'])!.value,
      key: this.editForm.get(['key'])!.value,
      secret: this.editForm.get(['secret'])!.value,
      view: this.editForm.get(['view'])!.value,
      orders: this.editForm.get(['orders'])!.value,
      withdraw: this.editForm.get(['withdraw'])!.value,
      nonce: this.editForm.get(['nonce'])!.value,
      siteUser: this.editForm.get(['siteUser'])!.value,
    };
  }
}
