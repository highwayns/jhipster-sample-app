import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import dayjs from 'dayjs/esm';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';

import { IOrder, Order } from '../order.model';
import { OrderService } from '../service/order.service';
import { IAddress } from 'app/entities/address/address.model';
import { AddressService } from 'app/entities/address/service/address.service';
import { IIdentity } from 'app/entities/identity/identity.model';
import { IdentityService } from 'app/entities/identity/service/identity.service';
import { IOrderLine } from 'app/entities/order-line/order-line.model';
import { OrderLineService } from 'app/entities/order-line/service/order-line.service';

@Component({
  selector: 'jhi-order-update',
  templateUrl: './order-update.component.html',
})
export class OrderUpdateComponent implements OnInit {
  isSaving = false;

  billingAddressesCollection: IAddress[] = [];
  shippingAddressesCollection: IAddress[] = [];
  billingIdentitiesCollection: IIdentity[] = [];
  orderLinesSharedCollection: IOrderLine[] = [];

  editForm = this.fb.group({
    id: [],
    orderNumber: [],
    note: [],
    createDateTimeUtc: [],
    customerReference: [],
    billingAddress: [],
    billingIdentity: [],
    shippingAddress: [],
    orderLines: [],
  });

  constructor(
    protected orderService: OrderService,
    protected addressService: AddressService,
    protected identityService: IdentityService,
    protected orderLineService: OrderLineService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ order }) => {
      if (order.id === undefined) {
        const today = dayjs().startOf('day');
        order.createDateTimeUtc = today;
      }

      this.updateForm(order);

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const order = this.createFromForm();
    if (order.id !== undefined) {
      this.subscribeToSaveResponse(this.orderService.update(order));
    } else {
      this.subscribeToSaveResponse(this.orderService.create(order));
    }
  }

  trackAddressById(_index: number, item: IAddress): number {
    return item.id!;
  }

  trackIdentityById(_index: number, item: IIdentity): number {
    return item.id!;
  }

  trackOrderLineById(_index: number, item: IOrderLine): number {
    return item.id!;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IOrder>>): void {
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

  protected updateForm(order: IOrder): void {
    this.editForm.patchValue({
      id: order.id,
      orderNumber: order.orderNumber,
      note: order.note,
      createDateTimeUtc: order.createDateTimeUtc ? order.createDateTimeUtc.format(DATE_TIME_FORMAT) : null,
      customerReference: order.customerReference,
      billingAddress: order.billingAddress,
      billingIdentity: order.billingIdentity,
      shippingAddress: order.shippingAddress,
      orderLines: order.orderLines,
    });

    this.billingAddressesCollection = this.addressService.addAddressToCollectionIfMissing(
      this.billingAddressesCollection,
      order.billingAddress
    );
    this.shippingAddressesCollection = this.addressService.addAddressToCollectionIfMissing(
      this.shippingAddressesCollection,
      order.shippingAddress
    );
    this.billingIdentitiesCollection = this.identityService.addIdentityToCollectionIfMissing(
      this.billingIdentitiesCollection,
      order.billingIdentity
    );
    this.orderLinesSharedCollection = this.orderLineService.addOrderLineToCollectionIfMissing(
      this.orderLinesSharedCollection,
      order.orderLines
    );
  }

  protected loadRelationshipsOptions(): void {
    this.addressService
      .query({ filter: 'order-is-null' })
      .pipe(map((res: HttpResponse<IAddress[]>) => res.body ?? []))
      .pipe(
        map((addresses: IAddress[]) =>
          this.addressService.addAddressToCollectionIfMissing(addresses, this.editForm.get('billingAddress')!.value)
        )
      )
      .subscribe((addresses: IAddress[]) => (this.billingAddressesCollection = addresses));

    this.addressService
      .query({ filter: 'order-is-null' })
      .pipe(map((res: HttpResponse<IAddress[]>) => res.body ?? []))
      .pipe(
        map((addresses: IAddress[]) =>
          this.addressService.addAddressToCollectionIfMissing(addresses, this.editForm.get('shippingAddress')!.value)
        )
      )
      .subscribe((addresses: IAddress[]) => (this.shippingAddressesCollection = addresses));

    this.identityService
      .query({ filter: 'order-is-null' })
      .pipe(map((res: HttpResponse<IIdentity[]>) => res.body ?? []))
      .pipe(
        map((identities: IIdentity[]) =>
          this.identityService.addIdentityToCollectionIfMissing(identities, this.editForm.get('billingIdentity')!.value)
        )
      )
      .subscribe((identities: IIdentity[]) => (this.billingIdentitiesCollection = identities));

    this.orderLineService
      .query()
      .pipe(map((res: HttpResponse<IOrderLine[]>) => res.body ?? []))
      .pipe(
        map((orderLines: IOrderLine[]) =>
          this.orderLineService.addOrderLineToCollectionIfMissing(orderLines, this.editForm.get('orderLines')!.value)
        )
      )
      .subscribe((orderLines: IOrderLine[]) => (this.orderLinesSharedCollection = orderLines));
  }

  protected createFromForm(): IOrder {
    return {
      ...new Order(),
      id: this.editForm.get(['id'])!.value,
      orderNumber: this.editForm.get(['orderNumber'])!.value,
      note: this.editForm.get(['note'])!.value,
      createDateTimeUtc: this.editForm.get(['createDateTimeUtc'])!.value
        ? dayjs(this.editForm.get(['createDateTimeUtc'])!.value, DATE_TIME_FORMAT)
        : undefined,
      customerReference: this.editForm.get(['customerReference'])!.value,
      billingAddress: this.editForm.get(['billingAddress'])!.value,
      billingIdentity: this.editForm.get(['billingIdentity'])!.value,
      shippingAddress: this.editForm.get(['shippingAddress'])!.value,
      orderLines: this.editForm.get(['orderLines'])!.value,
    };
  }
}
