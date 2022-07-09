import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { IPaymentMethodInfo, PaymentMethodInfo } from '../payment-method-info.model';
import { PaymentMethodInfoService } from '../service/payment-method-info.service';
import { ICurrencys } from 'app/entities/currencys/currencys.model';
import { CurrencysService } from 'app/entities/currencys/service/currencys.service';
import { IIssuer } from 'app/entities/issuer/issuer.model';
import { IssuerService } from 'app/entities/issuer/service/issuer.service';
import { ICardTokenData } from 'app/entities/card-token-data/card-token-data.model';
import { CardTokenDataService } from 'app/entities/card-token-data/service/card-token-data.service';

@Component({
  selector: 'jhi-payment-method-info-update',
  templateUrl: './payment-method-info-update.component.html',
})
export class PaymentMethodInfoUpdateComponent implements OnInit {
  isSaving = false;

  currencysSharedCollection: ICurrencys[] = [];
  issuersSharedCollection: IIssuer[] = [];
  cardTokenDataSharedCollection: ICardTokenData[] = [];

  editForm = this.fb.group({
    id: [],
    paymentMethod: [],
    logo: [],
    supportsTokenisation: [],
    surchargeAmount: [],
    surchargeAmountExclVat: [],
    surchargeAmountVat: [],
    surchargeVatPercentage: [],
    description: [],
    currencies: [],
    issuerList: [],
    tokenizedCards: [],
  });

  constructor(
    protected paymentMethodInfoService: PaymentMethodInfoService,
    protected currencysService: CurrencysService,
    protected issuerService: IssuerService,
    protected cardTokenDataService: CardTokenDataService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ paymentMethodInfo }) => {
      this.updateForm(paymentMethodInfo);

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const paymentMethodInfo = this.createFromForm();
    if (paymentMethodInfo.id !== undefined) {
      this.subscribeToSaveResponse(this.paymentMethodInfoService.update(paymentMethodInfo));
    } else {
      this.subscribeToSaveResponse(this.paymentMethodInfoService.create(paymentMethodInfo));
    }
  }

  trackCurrencysById(_index: number, item: ICurrencys): number {
    return item.id!;
  }

  trackIssuerById(_index: number, item: IIssuer): string {
    return item.id!;
  }

  trackCardTokenDataById(_index: number, item: ICardTokenData): number {
    return item.id!;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IPaymentMethodInfo>>): void {
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

  protected updateForm(paymentMethodInfo: IPaymentMethodInfo): void {
    this.editForm.patchValue({
      id: paymentMethodInfo.id,
      paymentMethod: paymentMethodInfo.paymentMethod,
      logo: paymentMethodInfo.logo,
      supportsTokenisation: paymentMethodInfo.supportsTokenisation,
      surchargeAmount: paymentMethodInfo.surchargeAmount,
      surchargeAmountExclVat: paymentMethodInfo.surchargeAmountExclVat,
      surchargeAmountVat: paymentMethodInfo.surchargeAmountVat,
      surchargeVatPercentage: paymentMethodInfo.surchargeVatPercentage,
      description: paymentMethodInfo.description,
      currencies: paymentMethodInfo.currencies,
      issuerList: paymentMethodInfo.issuerList,
      tokenizedCards: paymentMethodInfo.tokenizedCards,
    });

    this.currencysSharedCollection = this.currencysService.addCurrencysToCollectionIfMissing(
      this.currencysSharedCollection,
      paymentMethodInfo.currencies
    );
    this.issuersSharedCollection = this.issuerService.addIssuerToCollectionIfMissing(
      this.issuersSharedCollection,
      paymentMethodInfo.issuerList
    );
    this.cardTokenDataSharedCollection = this.cardTokenDataService.addCardTokenDataToCollectionIfMissing(
      this.cardTokenDataSharedCollection,
      paymentMethodInfo.tokenizedCards
    );
  }

  protected loadRelationshipsOptions(): void {
    this.currencysService
      .query()
      .pipe(map((res: HttpResponse<ICurrencys[]>) => res.body ?? []))
      .pipe(
        map((currencys: ICurrencys[]) =>
          this.currencysService.addCurrencysToCollectionIfMissing(currencys, this.editForm.get('currencies')!.value)
        )
      )
      .subscribe((currencys: ICurrencys[]) => (this.currencysSharedCollection = currencys));

    this.issuerService
      .query()
      .pipe(map((res: HttpResponse<IIssuer[]>) => res.body ?? []))
      .pipe(map((issuers: IIssuer[]) => this.issuerService.addIssuerToCollectionIfMissing(issuers, this.editForm.get('issuerList')!.value)))
      .subscribe((issuers: IIssuer[]) => (this.issuersSharedCollection = issuers));

    this.cardTokenDataService
      .query()
      .pipe(map((res: HttpResponse<ICardTokenData[]>) => res.body ?? []))
      .pipe(
        map((cardTokenData: ICardTokenData[]) =>
          this.cardTokenDataService.addCardTokenDataToCollectionIfMissing(cardTokenData, this.editForm.get('tokenizedCards')!.value)
        )
      )
      .subscribe((cardTokenData: ICardTokenData[]) => (this.cardTokenDataSharedCollection = cardTokenData));
  }

  protected createFromForm(): IPaymentMethodInfo {
    return {
      ...new PaymentMethodInfo(),
      id: this.editForm.get(['id'])!.value,
      paymentMethod: this.editForm.get(['paymentMethod'])!.value,
      logo: this.editForm.get(['logo'])!.value,
      supportsTokenisation: this.editForm.get(['supportsTokenisation'])!.value,
      surchargeAmount: this.editForm.get(['surchargeAmount'])!.value,
      surchargeAmountExclVat: this.editForm.get(['surchargeAmountExclVat'])!.value,
      surchargeAmountVat: this.editForm.get(['surchargeAmountVat'])!.value,
      surchargeVatPercentage: this.editForm.get(['surchargeVatPercentage'])!.value,
      description: this.editForm.get(['description'])!.value,
      currencies: this.editForm.get(['currencies'])!.value,
      issuerList: this.editForm.get(['issuerList'])!.value,
      tokenizedCards: this.editForm.get(['tokenizedCards'])!.value,
    };
  }
}
