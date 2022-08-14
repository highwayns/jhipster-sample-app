import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import dayjs from 'dayjs/esm';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';

import { IOrderLog, OrderLog } from '../order-log.model';
import { OrderLogService } from '../service/order-log.service';
import { ISiteUsers } from 'app/entities/site-users/site-users.model';
import { SiteUsersService } from 'app/entities/site-users/service/site-users.service';
import { ICurrencies } from 'app/entities/currencies/currencies.model';
import { CurrenciesService } from 'app/entities/currencies/service/currencies.service';
import { IOrderTypes } from 'app/entities/order-types/order-types.model';
import { OrderTypesService } from 'app/entities/order-types/service/order-types.service';
import { YesNo } from 'app/entities/enumerations/yes-no.model';
import { Status } from 'app/entities/enumerations/status.model';

@Component({
  selector: 'jhi-order-log-update',
  templateUrl: './order-log-update.component.html',
})
export class OrderLogUpdateComponent implements OnInit {
  isSaving = false;
  yesNoValues = Object.keys(YesNo);
  statusValues = Object.keys(Status);

  siteUsersCollection: ISiteUsers[] = [];
  currenciesCollection: ICurrencies[] = [];
  orderTypesCollection: IOrderTypes[] = [];

  editForm = this.fb.group({
    id: [],
    date: [null, [Validators.required]],
    btc: [null, [Validators.required]],
    marketPrice: [null, [Validators.required]],
    btcPrice: [null, [Validators.required]],
    fiat: [null, [Validators.required]],
    pId: [null, [Validators.required]],
    stopPrice: [null, [Validators.required, Validators.maxLength(255)]],
    status: [null, [Validators.required]],
    btcRemaining: [null, [Validators.required]],
    siteUser: [],
    currency: [],
    orderType: [],
  });

  constructor(
    protected orderLogService: OrderLogService,
    protected siteUsersService: SiteUsersService,
    protected currenciesService: CurrenciesService,
    protected orderTypesService: OrderTypesService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ orderLog }) => {
      if (orderLog.id === undefined) {
        const today = dayjs().startOf('day');
        orderLog.date = today;
      }

      this.updateForm(orderLog);

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const orderLog = this.createFromForm();
    if (orderLog.id !== undefined) {
      this.subscribeToSaveResponse(this.orderLogService.update(orderLog));
    } else {
      this.subscribeToSaveResponse(this.orderLogService.create(orderLog));
    }
  }

  trackSiteUsersById(_index: number, item: ISiteUsers): number {
    return item.id!;
  }

  trackCurrenciesById(_index: number, item: ICurrencies): number {
    return item.id!;
  }

  trackOrderTypesById(_index: number, item: IOrderTypes): number {
    return item.id!;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IOrderLog>>): void {
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

  protected updateForm(orderLog: IOrderLog): void {
    this.editForm.patchValue({
      id: orderLog.id,
      date: orderLog.date ? orderLog.date.format(DATE_TIME_FORMAT) : null,
      btc: orderLog.btc,
      marketPrice: orderLog.marketPrice,
      btcPrice: orderLog.btcPrice,
      fiat: orderLog.fiat,
      pId: orderLog.pId,
      stopPrice: orderLog.stopPrice,
      status: orderLog.status,
      btcRemaining: orderLog.btcRemaining,
      siteUser: orderLog.siteUser,
      currency: orderLog.currency,
      orderType: orderLog.orderType,
    });

    this.siteUsersCollection = this.siteUsersService.addSiteUsersToCollectionIfMissing(this.siteUsersCollection, orderLog.siteUser);
    this.currenciesCollection = this.currenciesService.addCurrenciesToCollectionIfMissing(this.currenciesCollection, orderLog.currency);
    this.orderTypesCollection = this.orderTypesService.addOrderTypesToCollectionIfMissing(this.orderTypesCollection, orderLog.orderType);
  }

  protected loadRelationshipsOptions(): void {
    this.siteUsersService
      .query({ filter: 'orderlog-is-null' })
      .pipe(map((res: HttpResponse<ISiteUsers[]>) => res.body ?? []))
      .pipe(
        map((siteUsers: ISiteUsers[]) =>
          this.siteUsersService.addSiteUsersToCollectionIfMissing(siteUsers, this.editForm.get('siteUser')!.value)
        )
      )
      .subscribe((siteUsers: ISiteUsers[]) => (this.siteUsersCollection = siteUsers));

    this.currenciesService
      .query({ filter: 'orderlog-is-null' })
      .pipe(map((res: HttpResponse<ICurrencies[]>) => res.body ?? []))
      .pipe(
        map((currencies: ICurrencies[]) =>
          this.currenciesService.addCurrenciesToCollectionIfMissing(currencies, this.editForm.get('currency')!.value)
        )
      )
      .subscribe((currencies: ICurrencies[]) => (this.currenciesCollection = currencies));

    this.orderTypesService
      .query({ filter: 'orderlog-is-null' })
      .pipe(map((res: HttpResponse<IOrderTypes[]>) => res.body ?? []))
      .pipe(
        map((orderTypes: IOrderTypes[]) =>
          this.orderTypesService.addOrderTypesToCollectionIfMissing(orderTypes, this.editForm.get('orderType')!.value)
        )
      )
      .subscribe((orderTypes: IOrderTypes[]) => (this.orderTypesCollection = orderTypes));
  }

  protected createFromForm(): IOrderLog {
    return {
      ...new OrderLog(),
      id: this.editForm.get(['id'])!.value,
      date: this.editForm.get(['date'])!.value ? dayjs(this.editForm.get(['date'])!.value, DATE_TIME_FORMAT) : undefined,
      btc: this.editForm.get(['btc'])!.value,
      marketPrice: this.editForm.get(['marketPrice'])!.value,
      btcPrice: this.editForm.get(['btcPrice'])!.value,
      fiat: this.editForm.get(['fiat'])!.value,
      pId: this.editForm.get(['pId'])!.value,
      stopPrice: this.editForm.get(['stopPrice'])!.value,
      status: this.editForm.get(['status'])!.value,
      btcRemaining: this.editForm.get(['btcRemaining'])!.value,
      siteUser: this.editForm.get(['siteUser'])!.value,
      currency: this.editForm.get(['currency'])!.value,
      orderType: this.editForm.get(['orderType'])!.value,
    };
  }
}
