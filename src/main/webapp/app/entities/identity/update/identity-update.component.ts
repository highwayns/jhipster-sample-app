import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import dayjs from 'dayjs/esm';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';

import { IIdentity, Identity } from '../identity.model';
import { IdentityService } from '../service/identity.service';
import { Gender } from 'app/entities/enumerations/gender.model';

@Component({
  selector: 'jhi-identity-update',
  templateUrl: './identity-update.component.html',
})
export class IdentityUpdateComponent implements OnInit {
  isSaving = false;
  genderValues = Object.keys(Gender);

  editForm = this.fb.group({
    id: [],
    debtorId: [],
    emailAddress: [],
    gender: [],
    dateOfBirth: [],
    socialSecurityNumber: [],
    chamberOfCommerceNumber: [],
    vatNumber: [],
  });

  constructor(protected identityService: IdentityService, protected activatedRoute: ActivatedRoute, protected fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ identity }) => {
      if (identity.id === undefined) {
        const today = dayjs().startOf('day');
        identity.dateOfBirth = today;
      }

      this.updateForm(identity);
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const identity = this.createFromForm();
    if (identity.id !== undefined) {
      this.subscribeToSaveResponse(this.identityService.update(identity));
    } else {
      this.subscribeToSaveResponse(this.identityService.create(identity));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IIdentity>>): void {
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

  protected updateForm(identity: IIdentity): void {
    this.editForm.patchValue({
      id: identity.id,
      debtorId: identity.debtorId,
      emailAddress: identity.emailAddress,
      gender: identity.gender,
      dateOfBirth: identity.dateOfBirth ? identity.dateOfBirth.format(DATE_TIME_FORMAT) : null,
      socialSecurityNumber: identity.socialSecurityNumber,
      chamberOfCommerceNumber: identity.chamberOfCommerceNumber,
      vatNumber: identity.vatNumber,
    });
  }

  protected createFromForm(): IIdentity {
    return {
      ...new Identity(),
      id: this.editForm.get(['id'])!.value,
      debtorId: this.editForm.get(['debtorId'])!.value,
      emailAddress: this.editForm.get(['emailAddress'])!.value,
      gender: this.editForm.get(['gender'])!.value,
      dateOfBirth: this.editForm.get(['dateOfBirth'])!.value
        ? dayjs(this.editForm.get(['dateOfBirth'])!.value, DATE_TIME_FORMAT)
        : undefined,
      socialSecurityNumber: this.editForm.get(['socialSecurityNumber'])!.value,
      chamberOfCommerceNumber: this.editForm.get(['chamberOfCommerceNumber'])!.value,
      vatNumber: this.editForm.get(['vatNumber'])!.value,
    };
  }
}
