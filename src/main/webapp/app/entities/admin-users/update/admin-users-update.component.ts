import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { IAdminUsers, AdminUsers } from '../admin-users.model';
import { AdminUsersService } from '../service/admin-users.service';
import { IIsoCountries } from 'app/entities/iso-countries/iso-countries.model';
import { IsoCountriesService } from 'app/entities/iso-countries/service/iso-countries.service';
import { YesNo } from 'app/entities/enumerations/yes-no.model';

@Component({
  selector: 'jhi-admin-users-update',
  templateUrl: './admin-users-update.component.html',
})
export class AdminUsersUpdateComponent implements OnInit {
  isSaving = false;
  yesNoValues = Object.keys(YesNo);

  countryIdsCollection: IIsoCountries[] = [];

  editForm = this.fb.group({
    id: [],
    user: [null, [Validators.required, Validators.maxLength(50)]],
    pass: [null, [Validators.required, Validators.maxLength(50)]],
    firstName: [null, [Validators.required, Validators.maxLength(50)]],
    lastName: [null, [Validators.required, Validators.maxLength(50)]],
    company: [null, [Validators.required, Validators.maxLength(255)]],
    address: [null, [Validators.required, Validators.maxLength(255)]],
    city: [null, [Validators.required, Validators.maxLength(50)]],
    phone: [null, [Validators.required, Validators.maxLength(50)]],
    email: [null, [Validators.required, Validators.maxLength(50)]],
    website: [null, [Validators.required, Validators.maxLength(255)]],
    fId: [null, [Validators.required]],
    order: [null, [Validators.required]],
    isAdmin: [null, [Validators.required]],
    countryCode: [null, [Validators.required]],
    verifiedAuthy: [null, [Validators.required]],
    authyId: [null, [Validators.required, Validators.maxLength(255)]],
    countryId: [],
  });

  constructor(
    protected adminUsersService: AdminUsersService,
    protected isoCountriesService: IsoCountriesService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ adminUsers }) => {
      this.updateForm(adminUsers);

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const adminUsers = this.createFromForm();
    if (adminUsers.id !== undefined) {
      this.subscribeToSaveResponse(this.adminUsersService.update(adminUsers));
    } else {
      this.subscribeToSaveResponse(this.adminUsersService.create(adminUsers));
    }
  }

  trackIsoCountriesById(_index: number, item: IIsoCountries): number {
    return item.id!;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IAdminUsers>>): void {
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

  protected updateForm(adminUsers: IAdminUsers): void {
    this.editForm.patchValue({
      id: adminUsers.id,
      user: adminUsers.user,
      pass: adminUsers.pass,
      firstName: adminUsers.firstName,
      lastName: adminUsers.lastName,
      company: adminUsers.company,
      address: adminUsers.address,
      city: adminUsers.city,
      phone: adminUsers.phone,
      email: adminUsers.email,
      website: adminUsers.website,
      fId: adminUsers.fId,
      order: adminUsers.order,
      isAdmin: adminUsers.isAdmin,
      countryCode: adminUsers.countryCode,
      verifiedAuthy: adminUsers.verifiedAuthy,
      authyId: adminUsers.authyId,
      countryId: adminUsers.countryId,
    });

    this.countryIdsCollection = this.isoCountriesService.addIsoCountriesToCollectionIfMissing(
      this.countryIdsCollection,
      adminUsers.countryId
    );
  }

  protected loadRelationshipsOptions(): void {
    this.isoCountriesService
      .query({ filter: 'adminusers-is-null' })
      .pipe(map((res: HttpResponse<IIsoCountries[]>) => res.body ?? []))
      .pipe(
        map((isoCountries: IIsoCountries[]) =>
          this.isoCountriesService.addIsoCountriesToCollectionIfMissing(isoCountries, this.editForm.get('countryId')!.value)
        )
      )
      .subscribe((isoCountries: IIsoCountries[]) => (this.countryIdsCollection = isoCountries));
  }

  protected createFromForm(): IAdminUsers {
    return {
      ...new AdminUsers(),
      id: this.editForm.get(['id'])!.value,
      user: this.editForm.get(['user'])!.value,
      pass: this.editForm.get(['pass'])!.value,
      firstName: this.editForm.get(['firstName'])!.value,
      lastName: this.editForm.get(['lastName'])!.value,
      company: this.editForm.get(['company'])!.value,
      address: this.editForm.get(['address'])!.value,
      city: this.editForm.get(['city'])!.value,
      phone: this.editForm.get(['phone'])!.value,
      email: this.editForm.get(['email'])!.value,
      website: this.editForm.get(['website'])!.value,
      fId: this.editForm.get(['fId'])!.value,
      order: this.editForm.get(['order'])!.value,
      isAdmin: this.editForm.get(['isAdmin'])!.value,
      countryCode: this.editForm.get(['countryCode'])!.value,
      verifiedAuthy: this.editForm.get(['verifiedAuthy'])!.value,
      authyId: this.editForm.get(['authyId'])!.value,
      countryId: this.editForm.get(['countryId'])!.value,
    };
  }
}
