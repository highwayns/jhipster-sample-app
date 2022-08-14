import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import dayjs from 'dayjs/esm';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';

import { IBitcoinAddresses, BitcoinAddresses } from '../bitcoin-addresses.model';
import { BitcoinAddressesService } from '../service/bitcoin-addresses.service';
import { ISiteUsers } from 'app/entities/site-users/site-users.model';
import { SiteUsersService } from 'app/entities/site-users/service/site-users.service';
import { YesNo } from 'app/entities/enumerations/yes-no.model';

@Component({
  selector: 'jhi-bitcoin-addresses-update',
  templateUrl: './bitcoin-addresses-update.component.html',
})
export class BitcoinAddressesUpdateComponent implements OnInit {
  isSaving = false;
  yesNoValues = Object.keys(YesNo);

  siteUsersCollection: ISiteUsers[] = [];

  editForm = this.fb.group({
    id: [],
    address: [null, [Validators.required, Validators.maxLength(255)]],
    date: [null, [Validators.required]],
    systemAddress: [null, [Validators.required]],
    hotWallet: [null, [Validators.required]],
    warmWallet: [null, [Validators.required]],
    siteUser: [],
  });

  constructor(
    protected bitcoinAddressesService: BitcoinAddressesService,
    protected siteUsersService: SiteUsersService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ bitcoinAddresses }) => {
      if (bitcoinAddresses.id === undefined) {
        const today = dayjs().startOf('day');
        bitcoinAddresses.date = today;
      }

      this.updateForm(bitcoinAddresses);

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const bitcoinAddresses = this.createFromForm();
    if (bitcoinAddresses.id !== undefined) {
      this.subscribeToSaveResponse(this.bitcoinAddressesService.update(bitcoinAddresses));
    } else {
      this.subscribeToSaveResponse(this.bitcoinAddressesService.create(bitcoinAddresses));
    }
  }

  trackSiteUsersById(_index: number, item: ISiteUsers): number {
    return item.id!;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IBitcoinAddresses>>): void {
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

  protected updateForm(bitcoinAddresses: IBitcoinAddresses): void {
    this.editForm.patchValue({
      id: bitcoinAddresses.id,
      address: bitcoinAddresses.address,
      date: bitcoinAddresses.date ? bitcoinAddresses.date.format(DATE_TIME_FORMAT) : null,
      systemAddress: bitcoinAddresses.systemAddress,
      hotWallet: bitcoinAddresses.hotWallet,
      warmWallet: bitcoinAddresses.warmWallet,
      siteUser: bitcoinAddresses.siteUser,
    });

    this.siteUsersCollection = this.siteUsersService.addSiteUsersToCollectionIfMissing(this.siteUsersCollection, bitcoinAddresses.siteUser);
  }

  protected loadRelationshipsOptions(): void {
    this.siteUsersService
      .query({ filter: 'bitcoinaddresses-is-null' })
      .pipe(map((res: HttpResponse<ISiteUsers[]>) => res.body ?? []))
      .pipe(
        map((siteUsers: ISiteUsers[]) =>
          this.siteUsersService.addSiteUsersToCollectionIfMissing(siteUsers, this.editForm.get('siteUser')!.value)
        )
      )
      .subscribe((siteUsers: ISiteUsers[]) => (this.siteUsersCollection = siteUsers));
  }

  protected createFromForm(): IBitcoinAddresses {
    return {
      ...new BitcoinAddresses(),
      id: this.editForm.get(['id'])!.value,
      address: this.editForm.get(['address'])!.value,
      date: this.editForm.get(['date'])!.value ? dayjs(this.editForm.get(['date'])!.value, DATE_TIME_FORMAT) : undefined,
      systemAddress: this.editForm.get(['systemAddress'])!.value,
      hotWallet: this.editForm.get(['hotWallet'])!.value,
      warmWallet: this.editForm.get(['warmWallet'])!.value,
      siteUser: this.editForm.get(['siteUser'])!.value,
    };
  }
}
