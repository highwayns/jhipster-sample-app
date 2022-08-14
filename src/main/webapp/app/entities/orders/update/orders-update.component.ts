import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import dayjs from 'dayjs/esm';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';

import { IOrders, Orders } from '../orders.model';
import { OrdersService } from '../service/orders.service';
import { IOrderTypes } from 'app/entities/order-types/order-types.model';
import { OrderTypesService } from 'app/entities/order-types/service/order-types.service';
import { ISiteUsers } from 'app/entities/site-users/site-users.model';
import { SiteUsersService } from 'app/entities/site-users/service/site-users.service';
import { ICurrencies } from 'app/entities/currencies/currencies.model';
import { CurrenciesService } from 'app/entities/currencies/service/currencies.service';
import { IOrderLog } from 'app/entities/order-log/order-log.model';
import { OrderLogService } from 'app/entities/order-log/service/order-log.service';
import { YesNo } from 'app/entities/enumerations/yes-no.model';

@Component({
  selector: 'jhi-orders-update',
  templateUrl: './orders-update.component.html',
})
export class OrdersUpdateComponent implements OnInit {
  isSaving = false;
  yesNoValues = Object.keys(YesNo);

  orderTypesCollection: IOrderTypes[] = [];
  siteUsersCollection: ISiteUsers[] = [];
  currenciesCollection: ICurrencies[] = [];
  logIdsCollection: IOrderLog[] = [];

  editForm = this.fb.group({
    id: [],
    date: [null, [Validators.required]],
    btc: [null, [Validators.required]],
    fiat: [null, [Validators.required]],
    btcPrice: [null, [Validators.required]],
    marketPrice: [null, [Validators.required]],
    stopPrice: [null, [Validators.required]],
    orderType: [],
    siteUser: [],
    currency: [],
    logId: [],
  });

  constructor(
    protected ordersService: OrdersService,
    protected orderTypesService: OrderTypesService,
    protected siteUsersService: SiteUsersService,
    protected currenciesService: CurrenciesService,
    protected orderLogService: OrderLogService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ orders }) => {
      if (orders.id === undefined) {
        const today = dayjs().startOf('day');
        orders.date = today;
      }

      this.updateForm(orders);

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const orders = this.createFromForm();
    if (orders.id !== undefined) {
      this.subscribeToSaveResponse(this.ordersService.update(orders));
    } else {
      this.subscribeToSaveResponse(this.ordersService.create(orders));
    }
  }

  trackOrderTypesById(_index: number, item: IOrderTypes): number {
    return item.id!;
  }

  trackSiteUsersById(_index: number, item: ISiteUsers): number {
    return item.id!;
  }

  trackCurrenciesById(_index: number, item: ICurrencies): number {
    return item.id!;
  }

  trackOrderLogById(_index: number, item: IOrderLog): number {
    return item.id!;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IOrders>>): void {
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

  protected updateForm(orders: IOrders): void {
    this.editForm.patchValue({
      id: orders.id,
      date: orders.date ? orders.date.format(DATE_TIME_FORMAT) : null,
      btc: orders.btc,
      fiat: orders.fiat,
      btcPrice: orders.btcPrice,
      marketPrice: orders.marketPrice,
      stopPrice: orders.stopPrice,
      orderType: orders.orderType,
      siteUser: orders.siteUser,
      currency: orders.currency,
      logId: orders.logId,
    });

    this.orderTypesCollection = this.orderTypesService.addOrderTypesToCollectionIfMissing(this.orderTypesCollection, orders.orderType);
    this.siteUsersCollection = this.siteUsersService.addSiteUsersToCollectionIfMissing(this.siteUsersCollection, orders.siteUser);
    this.currenciesCollection = this.currenciesService.addCurrenciesToCollectionIfMissing(this.currenciesCollection, orders.currency);
    this.logIdsCollection = this.orderLogService.addOrderLogToCollectionIfMissing(this.logIdsCollection, orders.logId);
  }

  protected loadRelationshipsOptions(): void {
    this.orderTypesService
      .query({ filter: 'orders-is-null' })
      .pipe(map((res: HttpResponse<IOrderTypes[]>) => res.body ?? []))
      .pipe(
        map((orderTypes: IOrderTypes[]) =>
          this.orderTypesService.addOrderTypesToCollectionIfMissing(orderTypes, this.editForm.get('orderType')!.value)
        )
      )
      .subscribe((orderTypes: IOrderTypes[]) => (this.orderTypesCollection = orderTypes));

    this.siteUsersService
      .query({ filter: 'orders-is-null' })
      .pipe(map((res: HttpResponse<ISiteUsers[]>) => res.body ?? []))
      .pipe(
        map((siteUsers: ISiteUsers[]) =>
          this.siteUsersService.addSiteUsersToCollectionIfMissing(siteUsers, this.editForm.get('siteUser')!.value)
        )
      )
      .subscribe((siteUsers: ISiteUsers[]) => (this.siteUsersCollection = siteUsers));

    this.currenciesService
      .query({ filter: 'orders-is-null' })
      .pipe(map((res: HttpResponse<ICurrencies[]>) => res.body ?? []))
      .pipe(
        map((currencies: ICurrencies[]) =>
          this.currenciesService.addCurrenciesToCollectionIfMissing(currencies, this.editForm.get('currency')!.value)
        )
      )
      .subscribe((currencies: ICurrencies[]) => (this.currenciesCollection = currencies));

    this.orderLogService
      .query({ filter: 'orders-is-null' })
      .pipe(map((res: HttpResponse<IOrderLog[]>) => res.body ?? []))
      .pipe(
        map((orderLogs: IOrderLog[]) => this.orderLogService.addOrderLogToCollectionIfMissing(orderLogs, this.editForm.get('logId')!.value))
      )
      .subscribe((orderLogs: IOrderLog[]) => (this.logIdsCollection = orderLogs));
  }

  protected createFromForm(): IOrders {
    return {
      ...new Orders(),
      id: this.editForm.get(['id'])!.value,
      date: this.editForm.get(['date'])!.value ? dayjs(this.editForm.get(['date'])!.value, DATE_TIME_FORMAT) : undefined,
      btc: this.editForm.get(['btc'])!.value,
      fiat: this.editForm.get(['fiat'])!.value,
      btcPrice: this.editForm.get(['btcPrice'])!.value,
      marketPrice: this.editForm.get(['marketPrice'])!.value,
      stopPrice: this.editForm.get(['stopPrice'])!.value,
      orderType: this.editForm.get(['orderType'])!.value,
      siteUser: this.editForm.get(['siteUser'])!.value,
      currency: this.editForm.get(['currency'])!.value,
      logId: this.editForm.get(['logId'])!.value,
    };
  }
}
