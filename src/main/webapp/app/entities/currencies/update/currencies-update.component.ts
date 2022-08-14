import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { ICurrencies, Currencies } from '../currencies.model';
import { CurrenciesService } from '../service/currencies.service';
import { YesNo } from 'app/entities/enumerations/yes-no.model';

@Component({
  selector: 'jhi-currencies-update',
  templateUrl: './currencies-update.component.html',
})
export class CurrenciesUpdateComponent implements OnInit {
  isSaving = false;
  yesNoValues = Object.keys(YesNo);

  editForm = this.fb.group({
    id: [],
    currency: [null, [Validators.required, Validators.maxLength(3)]],
    faSymbol: [null, [Validators.required, Validators.maxLength(255)]],
    accountNumber: [null, [Validators.required]],
    accountName: [null, [Validators.required, Validators.maxLength(255)]],
    isActive: [null, [Validators.required]],
    usdBid: [null, [Validators.required, Validators.maxLength(255)]],
    usdAsk: [null, [Validators.required, Validators.maxLength(255)]],
    nameEn: [null, [Validators.required, Validators.maxLength(255)]],
    nameEs: [null, [Validators.required, Validators.maxLength(255)]],
    nameRu: [null, [Validators.required, Validators.maxLength(255)]],
    nameZh: [null, [Validators.required, Validators.maxLength(255)]],
  });

  constructor(protected currenciesService: CurrenciesService, protected activatedRoute: ActivatedRoute, protected fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ currencies }) => {
      this.updateForm(currencies);
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const currencies = this.createFromForm();
    if (currencies.id !== undefined) {
      this.subscribeToSaveResponse(this.currenciesService.update(currencies));
    } else {
      this.subscribeToSaveResponse(this.currenciesService.create(currencies));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ICurrencies>>): void {
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

  protected updateForm(currencies: ICurrencies): void {
    this.editForm.patchValue({
      id: currencies.id,
      currency: currencies.currency,
      faSymbol: currencies.faSymbol,
      accountNumber: currencies.accountNumber,
      accountName: currencies.accountName,
      isActive: currencies.isActive,
      usdBid: currencies.usdBid,
      usdAsk: currencies.usdAsk,
      nameEn: currencies.nameEn,
      nameEs: currencies.nameEs,
      nameRu: currencies.nameRu,
      nameZh: currencies.nameZh,
    });
  }

  protected createFromForm(): ICurrencies {
    return {
      ...new Currencies(),
      id: this.editForm.get(['id'])!.value,
      currency: this.editForm.get(['currency'])!.value,
      faSymbol: this.editForm.get(['faSymbol'])!.value,
      accountNumber: this.editForm.get(['accountNumber'])!.value,
      accountName: this.editForm.get(['accountName'])!.value,
      isActive: this.editForm.get(['isActive'])!.value,
      usdBid: this.editForm.get(['usdBid'])!.value,
      usdAsk: this.editForm.get(['usdAsk'])!.value,
      nameEn: this.editForm.get(['nameEn'])!.value,
      nameEs: this.editForm.get(['nameEs'])!.value,
      nameRu: this.editForm.get(['nameRu'])!.value,
      nameZh: this.editForm.get(['nameZh'])!.value,
    };
  }
}
