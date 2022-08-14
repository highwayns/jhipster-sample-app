import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import dayjs from 'dayjs/esm';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';

import { ITransactions, Transactions } from '../transactions.model';
import { TransactionsService } from '../service/transactions.service';
import { ISiteUsers } from 'app/entities/site-users/site-users.model';
import { SiteUsersService } from 'app/entities/site-users/service/site-users.service';
import { ITransactionTypes } from 'app/entities/transaction-types/transaction-types.model';
import { TransactionTypesService } from 'app/entities/transaction-types/service/transaction-types.service';
import { ICurrencies } from 'app/entities/currencies/currencies.model';
import { CurrenciesService } from 'app/entities/currencies/service/currencies.service';
import { YesNo } from 'app/entities/enumerations/yes-no.model';

@Component({
  selector: 'jhi-transactions-update',
  templateUrl: './transactions-update.component.html',
})
export class TransactionsUpdateComponent implements OnInit {
  isSaving = false;
  yesNoValues = Object.keys(YesNo);

  siteUsersCollection: ISiteUsers[] = [];
  siteUser1sCollection: ISiteUsers[] = [];
  transactionTypesCollection: ITransactionTypes[] = [];
  transactionType1sCollection: ITransactionTypes[] = [];
  currency1sCollection: ICurrencies[] = [];
  convertFromCurrenciesCollection: ICurrencies[] = [];
  convertToCurrenciesCollection: ICurrencies[] = [];

  editForm = this.fb.group({
    id: [],
    date: [null, [Validators.required]],
    btc: [null, [Validators.required]],
    btcPrice: [null, [Validators.required]],
    fiat: [null, [Validators.required]],
    fee: [null, [Validators.required]],
    fee1: [null, [Validators.required]],
    btcNet: [null, [Validators.required]],
    btcNet1: [null, [Validators.required]],
    btcBefore1: [null, [Validators.required]],
    btcAfter1: [null, [Validators.required]],
    fiatBefore1: [null, [Validators.required]],
    fiatAfter1: [null, [Validators.required]],
    btcBefore: [null, [Validators.required]],
    btcAfter: [null, [Validators.required]],
    fiatBefore: [null, [Validators.required]],
    fiatAfter: [null, [Validators.required]],
    feeLevel: [null, [Validators.required]],
    feeLevel1: [null, [Validators.required]],
    origBtcPrice: [null, [Validators.required]],
    conversionFee: [null, [Validators.required]],
    convertAmount: [null, [Validators.required]],
    convertRateGiven: [null, [Validators.required]],
    convertSystemRate: [null, [Validators.required]],
    conversion: [null, [Validators.required]],
    bidAtTransaction: [null, [Validators.required]],
    askAtTransaction: [null, [Validators.required]],
    factored: [null, [Validators.required]],
    siteUser: [],
    siteUser1: [],
    transactionType: [],
    transactionType1: [],
    currency1: [],
    convertFromCurrency: [],
    convertToCurrency: [],
  });

  constructor(
    protected transactionsService: TransactionsService,
    protected siteUsersService: SiteUsersService,
    protected transactionTypesService: TransactionTypesService,
    protected currenciesService: CurrenciesService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ transactions }) => {
      if (transactions.id === undefined) {
        const today = dayjs().startOf('day');
        transactions.date = today;
      }

      this.updateForm(transactions);

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const transactions = this.createFromForm();
    if (transactions.id !== undefined) {
      this.subscribeToSaveResponse(this.transactionsService.update(transactions));
    } else {
      this.subscribeToSaveResponse(this.transactionsService.create(transactions));
    }
  }

  trackSiteUsersById(_index: number, item: ISiteUsers): number {
    return item.id!;
  }

  trackTransactionTypesById(_index: number, item: ITransactionTypes): number {
    return item.id!;
  }

  trackCurrenciesById(_index: number, item: ICurrencies): number {
    return item.id!;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ITransactions>>): void {
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

  protected updateForm(transactions: ITransactions): void {
    this.editForm.patchValue({
      id: transactions.id,
      date: transactions.date ? transactions.date.format(DATE_TIME_FORMAT) : null,
      btc: transactions.btc,
      btcPrice: transactions.btcPrice,
      fiat: transactions.fiat,
      fee: transactions.fee,
      fee1: transactions.fee1,
      btcNet: transactions.btcNet,
      btcNet1: transactions.btcNet1,
      btcBefore1: transactions.btcBefore1,
      btcAfter1: transactions.btcAfter1,
      fiatBefore1: transactions.fiatBefore1,
      fiatAfter1: transactions.fiatAfter1,
      btcBefore: transactions.btcBefore,
      btcAfter: transactions.btcAfter,
      fiatBefore: transactions.fiatBefore,
      fiatAfter: transactions.fiatAfter,
      feeLevel: transactions.feeLevel,
      feeLevel1: transactions.feeLevel1,
      origBtcPrice: transactions.origBtcPrice,
      conversionFee: transactions.conversionFee,
      convertAmount: transactions.convertAmount,
      convertRateGiven: transactions.convertRateGiven,
      convertSystemRate: transactions.convertSystemRate,
      conversion: transactions.conversion,
      bidAtTransaction: transactions.bidAtTransaction,
      askAtTransaction: transactions.askAtTransaction,
      factored: transactions.factored,
      siteUser: transactions.siteUser,
      siteUser1: transactions.siteUser1,
      transactionType: transactions.transactionType,
      transactionType1: transactions.transactionType1,
      currency1: transactions.currency1,
      convertFromCurrency: transactions.convertFromCurrency,
      convertToCurrency: transactions.convertToCurrency,
    });

    this.siteUsersCollection = this.siteUsersService.addSiteUsersToCollectionIfMissing(this.siteUsersCollection, transactions.siteUser);
    this.siteUser1sCollection = this.siteUsersService.addSiteUsersToCollectionIfMissing(this.siteUser1sCollection, transactions.siteUser1);
    this.transactionTypesCollection = this.transactionTypesService.addTransactionTypesToCollectionIfMissing(
      this.transactionTypesCollection,
      transactions.transactionType
    );
    this.transactionType1sCollection = this.transactionTypesService.addTransactionTypesToCollectionIfMissing(
      this.transactionType1sCollection,
      transactions.transactionType1
    );
    this.currency1sCollection = this.currenciesService.addCurrenciesToCollectionIfMissing(
      this.currency1sCollection,
      transactions.currency1
    );
    this.convertFromCurrenciesCollection = this.currenciesService.addCurrenciesToCollectionIfMissing(
      this.convertFromCurrenciesCollection,
      transactions.convertFromCurrency
    );
    this.convertToCurrenciesCollection = this.currenciesService.addCurrenciesToCollectionIfMissing(
      this.convertToCurrenciesCollection,
      transactions.convertToCurrency
    );
  }

  protected loadRelationshipsOptions(): void {
    this.siteUsersService
      .query({ filter: 'transactions-is-null' })
      .pipe(map((res: HttpResponse<ISiteUsers[]>) => res.body ?? []))
      .pipe(
        map((siteUsers: ISiteUsers[]) =>
          this.siteUsersService.addSiteUsersToCollectionIfMissing(siteUsers, this.editForm.get('siteUser')!.value)
        )
      )
      .subscribe((siteUsers: ISiteUsers[]) => (this.siteUsersCollection = siteUsers));

    this.siteUsersService
      .query({ filter: 'transactions-is-null' })
      .pipe(map((res: HttpResponse<ISiteUsers[]>) => res.body ?? []))
      .pipe(
        map((siteUsers: ISiteUsers[]) =>
          this.siteUsersService.addSiteUsersToCollectionIfMissing(siteUsers, this.editForm.get('siteUser1')!.value)
        )
      )
      .subscribe((siteUsers: ISiteUsers[]) => (this.siteUser1sCollection = siteUsers));

    this.transactionTypesService
      .query({ filter: 'transactions-is-null' })
      .pipe(map((res: HttpResponse<ITransactionTypes[]>) => res.body ?? []))
      .pipe(
        map((transactionTypes: ITransactionTypes[]) =>
          this.transactionTypesService.addTransactionTypesToCollectionIfMissing(
            transactionTypes,
            this.editForm.get('transactionType')!.value
          )
        )
      )
      .subscribe((transactionTypes: ITransactionTypes[]) => (this.transactionTypesCollection = transactionTypes));

    this.transactionTypesService
      .query({ filter: 'transactions-is-null' })
      .pipe(map((res: HttpResponse<ITransactionTypes[]>) => res.body ?? []))
      .pipe(
        map((transactionTypes: ITransactionTypes[]) =>
          this.transactionTypesService.addTransactionTypesToCollectionIfMissing(
            transactionTypes,
            this.editForm.get('transactionType1')!.value
          )
        )
      )
      .subscribe((transactionTypes: ITransactionTypes[]) => (this.transactionType1sCollection = transactionTypes));

    this.currenciesService
      .query({ filter: 'transactions-is-null' })
      .pipe(map((res: HttpResponse<ICurrencies[]>) => res.body ?? []))
      .pipe(
        map((currencies: ICurrencies[]) =>
          this.currenciesService.addCurrenciesToCollectionIfMissing(currencies, this.editForm.get('currency1')!.value)
        )
      )
      .subscribe((currencies: ICurrencies[]) => (this.currency1sCollection = currencies));

    this.currenciesService
      .query({ filter: 'transactions-is-null' })
      .pipe(map((res: HttpResponse<ICurrencies[]>) => res.body ?? []))
      .pipe(
        map((currencies: ICurrencies[]) =>
          this.currenciesService.addCurrenciesToCollectionIfMissing(currencies, this.editForm.get('convertFromCurrency')!.value)
        )
      )
      .subscribe((currencies: ICurrencies[]) => (this.convertFromCurrenciesCollection = currencies));

    this.currenciesService
      .query({ filter: 'transactions-is-null' })
      .pipe(map((res: HttpResponse<ICurrencies[]>) => res.body ?? []))
      .pipe(
        map((currencies: ICurrencies[]) =>
          this.currenciesService.addCurrenciesToCollectionIfMissing(currencies, this.editForm.get('convertToCurrency')!.value)
        )
      )
      .subscribe((currencies: ICurrencies[]) => (this.convertToCurrenciesCollection = currencies));
  }

  protected createFromForm(): ITransactions {
    return {
      ...new Transactions(),
      id: this.editForm.get(['id'])!.value,
      date: this.editForm.get(['date'])!.value ? dayjs(this.editForm.get(['date'])!.value, DATE_TIME_FORMAT) : undefined,
      btc: this.editForm.get(['btc'])!.value,
      btcPrice: this.editForm.get(['btcPrice'])!.value,
      fiat: this.editForm.get(['fiat'])!.value,
      fee: this.editForm.get(['fee'])!.value,
      fee1: this.editForm.get(['fee1'])!.value,
      btcNet: this.editForm.get(['btcNet'])!.value,
      btcNet1: this.editForm.get(['btcNet1'])!.value,
      btcBefore1: this.editForm.get(['btcBefore1'])!.value,
      btcAfter1: this.editForm.get(['btcAfter1'])!.value,
      fiatBefore1: this.editForm.get(['fiatBefore1'])!.value,
      fiatAfter1: this.editForm.get(['fiatAfter1'])!.value,
      btcBefore: this.editForm.get(['btcBefore'])!.value,
      btcAfter: this.editForm.get(['btcAfter'])!.value,
      fiatBefore: this.editForm.get(['fiatBefore'])!.value,
      fiatAfter: this.editForm.get(['fiatAfter'])!.value,
      feeLevel: this.editForm.get(['feeLevel'])!.value,
      feeLevel1: this.editForm.get(['feeLevel1'])!.value,
      origBtcPrice: this.editForm.get(['origBtcPrice'])!.value,
      conversionFee: this.editForm.get(['conversionFee'])!.value,
      convertAmount: this.editForm.get(['convertAmount'])!.value,
      convertRateGiven: this.editForm.get(['convertRateGiven'])!.value,
      convertSystemRate: this.editForm.get(['convertSystemRate'])!.value,
      conversion: this.editForm.get(['conversion'])!.value,
      bidAtTransaction: this.editForm.get(['bidAtTransaction'])!.value,
      askAtTransaction: this.editForm.get(['askAtTransaction'])!.value,
      factored: this.editForm.get(['factored'])!.value,
      siteUser: this.editForm.get(['siteUser'])!.value,
      siteUser1: this.editForm.get(['siteUser1'])!.value,
      transactionType: this.editForm.get(['transactionType'])!.value,
      transactionType1: this.editForm.get(['transactionType1'])!.value,
      currency1: this.editForm.get(['currency1'])!.value,
      convertFromCurrency: this.editForm.get(['convertFromCurrency'])!.value,
      convertToCurrency: this.editForm.get(['convertToCurrency'])!.value,
    };
  }
}
