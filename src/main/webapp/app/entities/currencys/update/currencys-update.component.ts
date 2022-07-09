import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { ICurrencys, Currencys } from '../currencys.model';
import { CurrencysService } from '../service/currencys.service';
import { Currency } from 'app/entities/enumerations/currency.model';

@Component({
  selector: 'jhi-currencys-update',
  templateUrl: './currencys-update.component.html',
})
export class CurrencysUpdateComponent implements OnInit {
  isSaving = false;
  currencyValues = Object.keys(Currency);

  editForm = this.fb.group({
    id: [],
    currency: [],
  });

  constructor(protected currencysService: CurrencysService, protected activatedRoute: ActivatedRoute, protected fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ currencys }) => {
      this.updateForm(currencys);
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const currencys = this.createFromForm();
    if (currencys.id !== undefined) {
      this.subscribeToSaveResponse(this.currencysService.update(currencys));
    } else {
      this.subscribeToSaveResponse(this.currencysService.create(currencys));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ICurrencys>>): void {
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

  protected updateForm(currencys: ICurrencys): void {
    this.editForm.patchValue({
      id: currencys.id,
      currency: currencys.currency,
    });
  }

  protected createFromForm(): ICurrencys {
    return {
      ...new Currencys(),
      id: this.editForm.get(['id'])!.value,
      currency: this.editForm.get(['currency'])!.value,
    };
  }
}
