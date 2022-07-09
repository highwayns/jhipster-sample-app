import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { IAddress, Address } from '../address.model';
import { AddressService } from '../service/address.service';
import { Country } from 'app/entities/enumerations/country.model';
import { PhoneNumberType } from 'app/entities/enumerations/phone-number-type.model';

@Component({
  selector: 'jhi-address-update',
  templateUrl: './address-update.component.html',
})
export class AddressUpdateComponent implements OnInit {
  isSaving = false;
  countryValues = Object.keys(Country);
  phoneNumberTypeValues = Object.keys(PhoneNumberType);

  editForm = this.fb.group({
    id: [],
    title: [],
    firstName: [],
    middleName: [],
    lastName: [],
    country: [],
    addressLine1: [],
    addressLine2: [],
    zipCode: [],
    city: [],
    stateProvince: [],
    phoneNumber1: [],
    phoneNumber1Type: [],
    phoneNumber2: [],
    phoneNumber2Type: [],
    organisation: [],
    department: [],
  });

  constructor(protected addressService: AddressService, protected activatedRoute: ActivatedRoute, protected fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ address }) => {
      this.updateForm(address);
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const address = this.createFromForm();
    if (address.id !== undefined) {
      this.subscribeToSaveResponse(this.addressService.update(address));
    } else {
      this.subscribeToSaveResponse(this.addressService.create(address));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IAddress>>): void {
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

  protected updateForm(address: IAddress): void {
    this.editForm.patchValue({
      id: address.id,
      title: address.title,
      firstName: address.firstName,
      middleName: address.middleName,
      lastName: address.lastName,
      country: address.country,
      addressLine1: address.addressLine1,
      addressLine2: address.addressLine2,
      zipCode: address.zipCode,
      city: address.city,
      stateProvince: address.stateProvince,
      phoneNumber1: address.phoneNumber1,
      phoneNumber1Type: address.phoneNumber1Type,
      phoneNumber2: address.phoneNumber2,
      phoneNumber2Type: address.phoneNumber2Type,
      organisation: address.organisation,
      department: address.department,
    });
  }

  protected createFromForm(): IAddress {
    return {
      ...new Address(),
      id: this.editForm.get(['id'])!.value,
      title: this.editForm.get(['title'])!.value,
      firstName: this.editForm.get(['firstName'])!.value,
      middleName: this.editForm.get(['middleName'])!.value,
      lastName: this.editForm.get(['lastName'])!.value,
      country: this.editForm.get(['country'])!.value,
      addressLine1: this.editForm.get(['addressLine1'])!.value,
      addressLine2: this.editForm.get(['addressLine2'])!.value,
      zipCode: this.editForm.get(['zipCode'])!.value,
      city: this.editForm.get(['city'])!.value,
      stateProvince: this.editForm.get(['stateProvince'])!.value,
      phoneNumber1: this.editForm.get(['phoneNumber1'])!.value,
      phoneNumber1Type: this.editForm.get(['phoneNumber1Type'])!.value,
      phoneNumber2: this.editForm.get(['phoneNumber2'])!.value,
      phoneNumber2Type: this.editForm.get(['phoneNumber2Type'])!.value,
      organisation: this.editForm.get(['organisation'])!.value,
      department: this.editForm.get(['department'])!.value,
    };
  }
}
