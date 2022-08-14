import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import dayjs from 'dayjs/esm';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';

import { IConversions, Conversions } from '../conversions.model';
import { ConversionsService } from '../service/conversions.service';
import { ICurrencies } from 'app/entities/currencies/currencies.model';
import { CurrenciesService } from 'app/entities/currencies/service/currencies.service';
import { YesNo } from 'app/entities/enumerations/yes-no.model';

@Component({
  selector: 'jhi-conversions-update',
  templateUrl: './conversions-update.component.html',
})
export class ConversionsUpdateComponent implements OnInit {
  isSaving = false;
  yesNoValues = Object.keys(YesNo);

  currenciesCollection: ICurrencies[] = [];

  editForm = this.fb.group({
    id: [],
    amount: [null, [Validators.required]],
    date: [null, [Validators.required]],
    isActive: [null, [Validators.required]],
    totalWithdrawals: [null, [Validators.required]],
    profitToFactor: [null, [Validators.required]],
    factored: [null, [Validators.required]],
    date1: [null, [Validators.required]],
    currency: [],
  });

  constructor(
    protected conversionsService: ConversionsService,
    protected currenciesService: CurrenciesService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ conversions }) => {
      if (conversions.id === undefined) {
        const today = dayjs().startOf('day');
        conversions.date = today;
        conversions.date1 = today;
      }

      this.updateForm(conversions);

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const conversions = this.createFromForm();
    if (conversions.id !== undefined) {
      this.subscribeToSaveResponse(this.conversionsService.update(conversions));
    } else {
      this.subscribeToSaveResponse(this.conversionsService.create(conversions));
    }
  }

  trackCurrenciesById(_index: number, item: ICurrencies): number {
    return item.id!;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IConversions>>): void {
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

  protected updateForm(conversions: IConversions): void {
    this.editForm.patchValue({
      id: conversions.id,
      amount: conversions.amount,
      date: conversions.date ? conversions.date.format(DATE_TIME_FORMAT) : null,
      isActive: conversions.isActive,
      totalWithdrawals: conversions.totalWithdrawals,
      profitToFactor: conversions.profitToFactor,
      factored: conversions.factored,
      date1: conversions.date1 ? conversions.date1.format(DATE_TIME_FORMAT) : null,
      currency: conversions.currency,
    });

    this.currenciesCollection = this.currenciesService.addCurrenciesToCollectionIfMissing(this.currenciesCollection, conversions.currency);
  }

  protected loadRelationshipsOptions(): void {
    this.currenciesService
      .query({ filter: 'conversions-is-null' })
      .pipe(map((res: HttpResponse<ICurrencies[]>) => res.body ?? []))
      .pipe(
        map((currencies: ICurrencies[]) =>
          this.currenciesService.addCurrenciesToCollectionIfMissing(currencies, this.editForm.get('currency')!.value)
        )
      )
      .subscribe((currencies: ICurrencies[]) => (this.currenciesCollection = currencies));
  }

  protected createFromForm(): IConversions {
    return {
      ...new Conversions(),
      id: this.editForm.get(['id'])!.value,
      amount: this.editForm.get(['amount'])!.value,
      date: this.editForm.get(['date'])!.value ? dayjs(this.editForm.get(['date'])!.value, DATE_TIME_FORMAT) : undefined,
      isActive: this.editForm.get(['isActive'])!.value,
      totalWithdrawals: this.editForm.get(['totalWithdrawals'])!.value,
      profitToFactor: this.editForm.get(['profitToFactor'])!.value,
      factored: this.editForm.get(['factored'])!.value,
      date1: this.editForm.get(['date1'])!.value ? dayjs(this.editForm.get(['date1'])!.value, DATE_TIME_FORMAT) : undefined,
      currency: this.editForm.get(['currency'])!.value,
    };
  }
}
