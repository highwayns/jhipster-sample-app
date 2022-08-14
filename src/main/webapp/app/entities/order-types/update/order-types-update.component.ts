import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { IOrderTypes, OrderTypes } from '../order-types.model';
import { OrderTypesService } from '../service/order-types.service';

@Component({
  selector: 'jhi-order-types-update',
  templateUrl: './order-types-update.component.html',
})
export class OrderTypesUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    nameEn: [null, [Validators.required, Validators.maxLength(255)]],
    nameEs: [null, [Validators.required, Validators.maxLength(255)]],
    nameRu: [null, [Validators.required, Validators.maxLength(255)]],
    nameZh: [null, [Validators.required, Validators.maxLength(255)]],
  });

  constructor(protected orderTypesService: OrderTypesService, protected activatedRoute: ActivatedRoute, protected fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ orderTypes }) => {
      this.updateForm(orderTypes);
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const orderTypes = this.createFromForm();
    if (orderTypes.id !== undefined) {
      this.subscribeToSaveResponse(this.orderTypesService.update(orderTypes));
    } else {
      this.subscribeToSaveResponse(this.orderTypesService.create(orderTypes));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IOrderTypes>>): void {
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

  protected updateForm(orderTypes: IOrderTypes): void {
    this.editForm.patchValue({
      id: orderTypes.id,
      nameEn: orderTypes.nameEn,
      nameEs: orderTypes.nameEs,
      nameRu: orderTypes.nameRu,
      nameZh: orderTypes.nameZh,
    });
  }

  protected createFromForm(): IOrderTypes {
    return {
      ...new OrderTypes(),
      id: this.editForm.get(['id'])!.value,
      nameEn: this.editForm.get(['nameEn'])!.value,
      nameEs: this.editForm.get(['nameEs'])!.value,
      nameRu: this.editForm.get(['nameRu'])!.value,
      nameZh: this.editForm.get(['nameZh'])!.value,
    };
  }
}
