import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { ISiteUsersBalances, SiteUsersBalances } from '../site-users-balances.model';
import { SiteUsersBalancesService } from '../service/site-users-balances.service';
import { ISiteUsers } from 'app/entities/site-users/site-users.model';
import { SiteUsersService } from 'app/entities/site-users/service/site-users.service';
import { ICurrencies } from 'app/entities/currencies/currencies.model';
import { CurrenciesService } from 'app/entities/currencies/service/currencies.service';

@Component({
  selector: 'jhi-site-users-balances-update',
  templateUrl: './site-users-balances-update.component.html',
})
export class SiteUsersBalancesUpdateComponent implements OnInit {
  isSaving = false;

  siteUsersCollection: ISiteUsers[] = [];
  currenciesCollection: ICurrencies[] = [];

  editForm = this.fb.group({
    id: [],
    balance: [null, [Validators.required]],
    siteUser: [],
    currency: [],
  });

  constructor(
    protected siteUsersBalancesService: SiteUsersBalancesService,
    protected siteUsersService: SiteUsersService,
    protected currenciesService: CurrenciesService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ siteUsersBalances }) => {
      this.updateForm(siteUsersBalances);

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const siteUsersBalances = this.createFromForm();
    if (siteUsersBalances.id !== undefined) {
      this.subscribeToSaveResponse(this.siteUsersBalancesService.update(siteUsersBalances));
    } else {
      this.subscribeToSaveResponse(this.siteUsersBalancesService.create(siteUsersBalances));
    }
  }

  trackSiteUsersById(_index: number, item: ISiteUsers): number {
    return item.id!;
  }

  trackCurrenciesById(_index: number, item: ICurrencies): number {
    return item.id!;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ISiteUsersBalances>>): void {
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

  protected updateForm(siteUsersBalances: ISiteUsersBalances): void {
    this.editForm.patchValue({
      id: siteUsersBalances.id,
      balance: siteUsersBalances.balance,
      siteUser: siteUsersBalances.siteUser,
      currency: siteUsersBalances.currency,
    });

    this.siteUsersCollection = this.siteUsersService.addSiteUsersToCollectionIfMissing(
      this.siteUsersCollection,
      siteUsersBalances.siteUser
    );
    this.currenciesCollection = this.currenciesService.addCurrenciesToCollectionIfMissing(
      this.currenciesCollection,
      siteUsersBalances.currency
    );
  }

  protected loadRelationshipsOptions(): void {
    this.siteUsersService
      .query({ filter: 'siteusersbalances-is-null' })
      .pipe(map((res: HttpResponse<ISiteUsers[]>) => res.body ?? []))
      .pipe(
        map((siteUsers: ISiteUsers[]) =>
          this.siteUsersService.addSiteUsersToCollectionIfMissing(siteUsers, this.editForm.get('siteUser')!.value)
        )
      )
      .subscribe((siteUsers: ISiteUsers[]) => (this.siteUsersCollection = siteUsers));

    this.currenciesService
      .query({ filter: 'siteusersbalances-is-null' })
      .pipe(map((res: HttpResponse<ICurrencies[]>) => res.body ?? []))
      .pipe(
        map((currencies: ICurrencies[]) =>
          this.currenciesService.addCurrenciesToCollectionIfMissing(currencies, this.editForm.get('currency')!.value)
        )
      )
      .subscribe((currencies: ICurrencies[]) => (this.currenciesCollection = currencies));
  }

  protected createFromForm(): ISiteUsersBalances {
    return {
      ...new SiteUsersBalances(),
      id: this.editForm.get(['id'])!.value,
      balance: this.editForm.get(['balance'])!.value,
      siteUser: this.editForm.get(['siteUser'])!.value,
      currency: this.editForm.get(['currency'])!.value,
    };
  }
}
