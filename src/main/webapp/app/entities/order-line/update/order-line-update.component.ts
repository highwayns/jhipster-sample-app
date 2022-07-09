import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { IOrderLine, OrderLine } from '../order-line.model';
import { OrderLineService } from '../service/order-line.service';
import { OrderLineType } from 'app/entities/enumerations/order-line-type.model';

@Component({
  selector: 'jhi-order-line-update',
  templateUrl: './order-line-update.component.html',
})
export class OrderLineUpdateComponent implements OnInit {
  isSaving = false;
  orderLineTypeValues = Object.keys(OrderLineType);

  editForm = this.fb.group({
    id: [],
    lineNumber: [],
    type: [],
    skuCode: [],
    name: [],
    description: [],
    quantity: [],
    unitPriceExclVat: [],
    unitPriceInclVat: [],
    vatPercentage: [],
    vatPercentageLabel: [],
    discountPercentageLabel: [],
    totalLineAmount: [],
    url: [],
  });

  constructor(protected orderLineService: OrderLineService, protected activatedRoute: ActivatedRoute, protected fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ orderLine }) => {
      this.updateForm(orderLine);
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const orderLine = this.createFromForm();
    if (orderLine.id !== undefined) {
      this.subscribeToSaveResponse(this.orderLineService.update(orderLine));
    } else {
      this.subscribeToSaveResponse(this.orderLineService.create(orderLine));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IOrderLine>>): void {
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

  protected updateForm(orderLine: IOrderLine): void {
    this.editForm.patchValue({
      id: orderLine.id,
      lineNumber: orderLine.lineNumber,
      type: orderLine.type,
      skuCode: orderLine.skuCode,
      name: orderLine.name,
      description: orderLine.description,
      quantity: orderLine.quantity,
      unitPriceExclVat: orderLine.unitPriceExclVat,
      unitPriceInclVat: orderLine.unitPriceInclVat,
      vatPercentage: orderLine.vatPercentage,
      vatPercentageLabel: orderLine.vatPercentageLabel,
      discountPercentageLabel: orderLine.discountPercentageLabel,
      totalLineAmount: orderLine.totalLineAmount,
      url: orderLine.url,
    });
  }

  protected createFromForm(): IOrderLine {
    return {
      ...new OrderLine(),
      id: this.editForm.get(['id'])!.value,
      lineNumber: this.editForm.get(['lineNumber'])!.value,
      type: this.editForm.get(['type'])!.value,
      skuCode: this.editForm.get(['skuCode'])!.value,
      name: this.editForm.get(['name'])!.value,
      description: this.editForm.get(['description'])!.value,
      quantity: this.editForm.get(['quantity'])!.value,
      unitPriceExclVat: this.editForm.get(['unitPriceExclVat'])!.value,
      unitPriceInclVat: this.editForm.get(['unitPriceInclVat'])!.value,
      vatPercentage: this.editForm.get(['vatPercentage'])!.value,
      vatPercentageLabel: this.editForm.get(['vatPercentageLabel'])!.value,
      discountPercentageLabel: this.editForm.get(['discountPercentageLabel'])!.value,
      totalLineAmount: this.editForm.get(['totalLineAmount'])!.value,
      url: this.editForm.get(['url'])!.value,
    };
  }
}
