import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import dayjs from 'dayjs/esm';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';

import { ISiteUsers, SiteUsers } from '../site-users.model';
import { SiteUsersService } from '../service/site-users.service';
import { IIsoCountries } from 'app/entities/iso-countries/iso-countries.model';
import { IsoCountriesService } from 'app/entities/iso-countries/service/iso-countries.service';
import { IFeeSchedule } from 'app/entities/fee-schedule/fee-schedule.model';
import { FeeScheduleService } from 'app/entities/fee-schedule/service/fee-schedule.service';
import { ICurrencies } from 'app/entities/currencies/currencies.model';
import { CurrenciesService } from 'app/entities/currencies/service/currencies.service';
import { YesNo } from 'app/entities/enumerations/yes-no.model';

@Component({
  selector: 'jhi-site-users-update',
  templateUrl: './site-users-update.component.html',
})
export class SiteUsersUpdateComponent implements OnInit {
  isSaving = false;
  yesNoValues = Object.keys(YesNo);

  countriesCollection: IIsoCountries[] = [];
  feeSchedulesCollection: IFeeSchedule[] = [];
  defaultCurrenciesCollection: ICurrencies[] = [];

  editForm = this.fb.group({
    id: [],
    pass: [null, [Validators.required, Validators.maxLength(200)]],
    firstName: [null, [Validators.required, Validators.maxLength(200)]],
    lastName: [null, [Validators.required, Validators.maxLength(200)]],
    email: [null, [Validators.required, Validators.maxLength(255)]],
    date: [null, [Validators.required]],
    tel: [null, [Validators.required, Validators.maxLength(255)]],
    user: [null, [Validators.required, Validators.maxLength(255)]],
    countryCode: [null, [Validators.required]],
    authyRequested: [null, [Validators.required]],
    verifiedAuthy: [null, [Validators.required]],
    authyId: [null, [Validators.required]],
    usingSms: [null, [Validators.required]],
    dontAsk30Days: [null, [Validators.required]],
    dontAskDate: [null, [Validators.required]],
    confirmWithdrawalEmailBtc: [null, [Validators.required]],
    confirmWithdrawal2faBtc: [null, [Validators.required]],
    confirmWithdrawal2faBank: [null, [Validators.required]],
    confirmWithdrawalEmailBank: [null, [Validators.required]],
    notifyDepositBtc: [null, [Validators.required]],
    notifyDepositBank: [null, [Validators.required]],
    lastUpdate: [null, [Validators.required]],
    noLogins: [null, [Validators.required]],
    notifyLogin: [null, [Validators.required]],
    deactivated: [null, [Validators.required]],
    locked: [null, [Validators.required]],
    google2faCode: [null, [Validators.required, Validators.maxLength(255)]],
    verifiedGoogle: [null, [Validators.required]],
    lastLang: [null, [Validators.required, Validators.maxLength(255)]],
    notifyWithdrawBtc: [null, [Validators.required]],
    notifyWithdrawBank: [null, [Validators.required]],
    trusted: [null, [Validators.required]],
    country: [],
    feeSchedule: [],
    defaultCurrency: [],
  });

  constructor(
    protected siteUsersService: SiteUsersService,
    protected isoCountriesService: IsoCountriesService,
    protected feeScheduleService: FeeScheduleService,
    protected currenciesService: CurrenciesService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ siteUsers }) => {
      if (siteUsers.id === undefined) {
        const today = dayjs().startOf('day');
        siteUsers.date = today;
        siteUsers.dontAskDate = today;
        siteUsers.lastUpdate = today;
      }

      this.updateForm(siteUsers);

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const siteUsers = this.createFromForm();
    if (siteUsers.id !== undefined) {
      this.subscribeToSaveResponse(this.siteUsersService.update(siteUsers));
    } else {
      this.subscribeToSaveResponse(this.siteUsersService.create(siteUsers));
    }
  }

  trackIsoCountriesById(_index: number, item: IIsoCountries): number {
    return item.id!;
  }

  trackFeeScheduleById(_index: number, item: IFeeSchedule): number {
    return item.id!;
  }

  trackCurrenciesById(_index: number, item: ICurrencies): number {
    return item.id!;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ISiteUsers>>): void {
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

  protected updateForm(siteUsers: ISiteUsers): void {
    this.editForm.patchValue({
      id: siteUsers.id,
      pass: siteUsers.pass,
      firstName: siteUsers.firstName,
      lastName: siteUsers.lastName,
      email: siteUsers.email,
      date: siteUsers.date ? siteUsers.date.format(DATE_TIME_FORMAT) : null,
      tel: siteUsers.tel,
      user: siteUsers.user,
      countryCode: siteUsers.countryCode,
      authyRequested: siteUsers.authyRequested,
      verifiedAuthy: siteUsers.verifiedAuthy,
      authyId: siteUsers.authyId,
      usingSms: siteUsers.usingSms,
      dontAsk30Days: siteUsers.dontAsk30Days,
      dontAskDate: siteUsers.dontAskDate ? siteUsers.dontAskDate.format(DATE_TIME_FORMAT) : null,
      confirmWithdrawalEmailBtc: siteUsers.confirmWithdrawalEmailBtc,
      confirmWithdrawal2faBtc: siteUsers.confirmWithdrawal2faBtc,
      confirmWithdrawal2faBank: siteUsers.confirmWithdrawal2faBank,
      confirmWithdrawalEmailBank: siteUsers.confirmWithdrawalEmailBank,
      notifyDepositBtc: siteUsers.notifyDepositBtc,
      notifyDepositBank: siteUsers.notifyDepositBank,
      lastUpdate: siteUsers.lastUpdate ? siteUsers.lastUpdate.format(DATE_TIME_FORMAT) : null,
      noLogins: siteUsers.noLogins,
      notifyLogin: siteUsers.notifyLogin,
      deactivated: siteUsers.deactivated,
      locked: siteUsers.locked,
      google2faCode: siteUsers.google2faCode,
      verifiedGoogle: siteUsers.verifiedGoogle,
      lastLang: siteUsers.lastLang,
      notifyWithdrawBtc: siteUsers.notifyWithdrawBtc,
      notifyWithdrawBank: siteUsers.notifyWithdrawBank,
      trusted: siteUsers.trusted,
      country: siteUsers.country,
      feeSchedule: siteUsers.feeSchedule,
      defaultCurrency: siteUsers.defaultCurrency,
    });

    this.countriesCollection = this.isoCountriesService.addIsoCountriesToCollectionIfMissing(this.countriesCollection, siteUsers.country);
    this.feeSchedulesCollection = this.feeScheduleService.addFeeScheduleToCollectionIfMissing(
      this.feeSchedulesCollection,
      siteUsers.feeSchedule
    );
    this.defaultCurrenciesCollection = this.currenciesService.addCurrenciesToCollectionIfMissing(
      this.defaultCurrenciesCollection,
      siteUsers.defaultCurrency
    );
  }

  protected loadRelationshipsOptions(): void {
    this.isoCountriesService
      .query({ filter: 'siteusers-is-null' })
      .pipe(map((res: HttpResponse<IIsoCountries[]>) => res.body ?? []))
      .pipe(
        map((isoCountries: IIsoCountries[]) =>
          this.isoCountriesService.addIsoCountriesToCollectionIfMissing(isoCountries, this.editForm.get('country')!.value)
        )
      )
      .subscribe((isoCountries: IIsoCountries[]) => (this.countriesCollection = isoCountries));

    this.feeScheduleService
      .query({ filter: 'siteusers-is-null' })
      .pipe(map((res: HttpResponse<IFeeSchedule[]>) => res.body ?? []))
      .pipe(
        map((feeSchedules: IFeeSchedule[]) =>
          this.feeScheduleService.addFeeScheduleToCollectionIfMissing(feeSchedules, this.editForm.get('feeSchedule')!.value)
        )
      )
      .subscribe((feeSchedules: IFeeSchedule[]) => (this.feeSchedulesCollection = feeSchedules));

    this.currenciesService
      .query({ filter: 'siteusers-is-null' })
      .pipe(map((res: HttpResponse<ICurrencies[]>) => res.body ?? []))
      .pipe(
        map((currencies: ICurrencies[]) =>
          this.currenciesService.addCurrenciesToCollectionIfMissing(currencies, this.editForm.get('defaultCurrency')!.value)
        )
      )
      .subscribe((currencies: ICurrencies[]) => (this.defaultCurrenciesCollection = currencies));
  }

  protected createFromForm(): ISiteUsers {
    return {
      ...new SiteUsers(),
      id: this.editForm.get(['id'])!.value,
      pass: this.editForm.get(['pass'])!.value,
      firstName: this.editForm.get(['firstName'])!.value,
      lastName: this.editForm.get(['lastName'])!.value,
      email: this.editForm.get(['email'])!.value,
      date: this.editForm.get(['date'])!.value ? dayjs(this.editForm.get(['date'])!.value, DATE_TIME_FORMAT) : undefined,
      tel: this.editForm.get(['tel'])!.value,
      user: this.editForm.get(['user'])!.value,
      countryCode: this.editForm.get(['countryCode'])!.value,
      authyRequested: this.editForm.get(['authyRequested'])!.value,
      verifiedAuthy: this.editForm.get(['verifiedAuthy'])!.value,
      authyId: this.editForm.get(['authyId'])!.value,
      usingSms: this.editForm.get(['usingSms'])!.value,
      dontAsk30Days: this.editForm.get(['dontAsk30Days'])!.value,
      dontAskDate: this.editForm.get(['dontAskDate'])!.value
        ? dayjs(this.editForm.get(['dontAskDate'])!.value, DATE_TIME_FORMAT)
        : undefined,
      confirmWithdrawalEmailBtc: this.editForm.get(['confirmWithdrawalEmailBtc'])!.value,
      confirmWithdrawal2faBtc: this.editForm.get(['confirmWithdrawal2faBtc'])!.value,
      confirmWithdrawal2faBank: this.editForm.get(['confirmWithdrawal2faBank'])!.value,
      confirmWithdrawalEmailBank: this.editForm.get(['confirmWithdrawalEmailBank'])!.value,
      notifyDepositBtc: this.editForm.get(['notifyDepositBtc'])!.value,
      notifyDepositBank: this.editForm.get(['notifyDepositBank'])!.value,
      lastUpdate: this.editForm.get(['lastUpdate'])!.value ? dayjs(this.editForm.get(['lastUpdate'])!.value, DATE_TIME_FORMAT) : undefined,
      noLogins: this.editForm.get(['noLogins'])!.value,
      notifyLogin: this.editForm.get(['notifyLogin'])!.value,
      deactivated: this.editForm.get(['deactivated'])!.value,
      locked: this.editForm.get(['locked'])!.value,
      google2faCode: this.editForm.get(['google2faCode'])!.value,
      verifiedGoogle: this.editForm.get(['verifiedGoogle'])!.value,
      lastLang: this.editForm.get(['lastLang'])!.value,
      notifyWithdrawBtc: this.editForm.get(['notifyWithdrawBtc'])!.value,
      notifyWithdrawBank: this.editForm.get(['notifyWithdrawBank'])!.value,
      trusted: this.editForm.get(['trusted'])!.value,
      country: this.editForm.get(['country'])!.value,
      feeSchedule: this.editForm.get(['feeSchedule'])!.value,
      defaultCurrency: this.editForm.get(['defaultCurrency'])!.value,
    };
  }
}
