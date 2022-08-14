import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { IBankAccounts, BankAccounts } from '../bank-accounts.model';
import { BankAccountsService } from '../service/bank-accounts.service';
import { ISiteUsers } from 'app/entities/site-users/site-users.model';
import { SiteUsersService } from 'app/entities/site-users/service/site-users.service';
import { ICurrencies } from 'app/entities/currencies/currencies.model';
import { CurrenciesService } from 'app/entities/currencies/service/currencies.service';

@Component({
  selector: 'jhi-bank-accounts-update',
  templateUrl: './bank-accounts-update.component.html',
})
export class BankAccountsUpdateComponent implements OnInit {
  isSaving = false;

  siteUsersCollection: ISiteUsers[] = [];
  currenciesCollection: ICurrencies[] = [];

  editForm = this.fb.group({
    id: [],
    accountNumber: [null, [Validators.required]],
    description: [null, [Validators.required, Validators.maxLength(255)]],
    siteUser: [],
    currency: [],
  });

  constructor(
    protected bankAccountsService: BankAccountsService,
    protected siteUsersService: SiteUsersService,
    protected currenciesService: CurrenciesService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ bankAccounts }) => {
      this.updateForm(bankAccounts);

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const bankAccounts = this.createFromForm();
    if (bankAccounts.id !== undefined) {
      this.subscribeToSaveResponse(this.bankAccountsService.update(bankAccounts));
    } else {
      this.subscribeToSaveResponse(this.bankAccountsService.create(bankAccounts));
    }
  }

  trackSiteUsersById(_index: number, item: ISiteUsers): number {
    return item.id!;
  }

  trackCurrenciesById(_index: number, item: ICurrencies): number {
    return item.id!;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IBankAccounts>>): void {
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

  protected updateForm(bankAccounts: IBankAccounts): void {
    this.editForm.patchValue({
      id: bankAccounts.id,
      accountNumber: bankAccounts.accountNumber,
      description: bankAccounts.description,
      siteUser: bankAccounts.siteUser,
      currency: bankAccounts.currency,
    });

    this.siteUsersCollection = this.siteUsersService.addSiteUsersToCollectionIfMissing(this.siteUsersCollection, bankAccounts.siteUser);
    this.currenciesCollection = this.currenciesService.addCurrenciesToCollectionIfMissing(this.currenciesCollection, bankAccounts.currency);
  }

  protected loadRelationshipsOptions(): void {
    this.siteUsersService
      .query({ filter: 'bankaccounts-is-null' })
      .pipe(map((res: HttpResponse<ISiteUsers[]>) => res.body ?? []))
      .pipe(
        map((siteUsers: ISiteUsers[]) =>
          this.siteUsersService.addSiteUsersToCollectionIfMissing(siteUsers, this.editForm.get('siteUser')!.value)
        )
      )
      .subscribe((siteUsers: ISiteUsers[]) => (this.siteUsersCollection = siteUsers));

    this.currenciesService
      .query({ filter: 'bankaccounts-is-null' })
      .pipe(map((res: HttpResponse<ICurrencies[]>) => res.body ?? []))
      .pipe(
        map((currencies: ICurrencies[]) =>
          this.currenciesService.addCurrenciesToCollectionIfMissing(currencies, this.editForm.get('currency')!.value)
        )
      )
      .subscribe((currencies: ICurrencies[]) => (this.currenciesCollection = currencies));
  }

  protected createFromForm(): IBankAccounts {
    return {
      ...new BankAccounts(),
      id: this.editForm.get(['id'])!.value,
      accountNumber: this.editForm.get(['accountNumber'])!.value,
      description: this.editForm.get(['description'])!.value,
      siteUser: this.editForm.get(['siteUser'])!.value,
      currency: this.editForm.get(['currency'])!.value,
    };
  }
}
