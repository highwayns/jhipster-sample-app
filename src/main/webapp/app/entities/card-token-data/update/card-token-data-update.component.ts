import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { ICardTokenData, CardTokenData } from '../card-token-data.model';
import { CardTokenDataService } from '../service/card-token-data.service';

@Component({
  selector: 'jhi-card-token-data-update',
  templateUrl: './card-token-data-update.component.html',
})
export class CardTokenDataUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    token: [],
    cardExpiryMonth: [],
    cardExpiryYear: [],
    issuerReturnCode: [],
    truncatedCardNumber: [],
  });

  constructor(protected cardTokenDataService: CardTokenDataService, protected activatedRoute: ActivatedRoute, protected fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ cardTokenData }) => {
      this.updateForm(cardTokenData);
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const cardTokenData = this.createFromForm();
    if (cardTokenData.id !== undefined) {
      this.subscribeToSaveResponse(this.cardTokenDataService.update(cardTokenData));
    } else {
      this.subscribeToSaveResponse(this.cardTokenDataService.create(cardTokenData));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ICardTokenData>>): void {
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

  protected updateForm(cardTokenData: ICardTokenData): void {
    this.editForm.patchValue({
      id: cardTokenData.id,
      token: cardTokenData.token,
      cardExpiryMonth: cardTokenData.cardExpiryMonth,
      cardExpiryYear: cardTokenData.cardExpiryYear,
      issuerReturnCode: cardTokenData.issuerReturnCode,
      truncatedCardNumber: cardTokenData.truncatedCardNumber,
    });
  }

  protected createFromForm(): ICardTokenData {
    return {
      ...new CardTokenData(),
      id: this.editForm.get(['id'])!.value,
      token: this.editForm.get(['token'])!.value,
      cardExpiryMonth: this.editForm.get(['cardExpiryMonth'])!.value,
      cardExpiryYear: this.editForm.get(['cardExpiryYear'])!.value,
      issuerReturnCode: this.editForm.get(['issuerReturnCode'])!.value,
      truncatedCardNumber: this.editForm.get(['truncatedCardNumber'])!.value,
    };
  }
}
