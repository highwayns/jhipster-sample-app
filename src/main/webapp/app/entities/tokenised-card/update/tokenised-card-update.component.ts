import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { ITokenisedCard, TokenisedCard } from '../tokenised-card.model';
import { TokenisedCardService } from '../service/tokenised-card.service';

@Component({
  selector: 'jhi-tokenised-card-update',
  templateUrl: './tokenised-card-update.component.html',
})
export class TokenisedCardUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    token: [],
    cardExpiryMonth: [],
    cardExpiryYear: [],
    truncatedCardNumber: [],
  });

  constructor(protected tokenisedCardService: TokenisedCardService, protected activatedRoute: ActivatedRoute, protected fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ tokenisedCard }) => {
      this.updateForm(tokenisedCard);
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const tokenisedCard = this.createFromForm();
    if (tokenisedCard.id !== undefined) {
      this.subscribeToSaveResponse(this.tokenisedCardService.update(tokenisedCard));
    } else {
      this.subscribeToSaveResponse(this.tokenisedCardService.create(tokenisedCard));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ITokenisedCard>>): void {
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

  protected updateForm(tokenisedCard: ITokenisedCard): void {
    this.editForm.patchValue({
      id: tokenisedCard.id,
      token: tokenisedCard.token,
      cardExpiryMonth: tokenisedCard.cardExpiryMonth,
      cardExpiryYear: tokenisedCard.cardExpiryYear,
      truncatedCardNumber: tokenisedCard.truncatedCardNumber,
    });
  }

  protected createFromForm(): ITokenisedCard {
    return {
      ...new TokenisedCard(),
      id: this.editForm.get(['id'])!.value,
      token: this.editForm.get(['token'])!.value,
      cardExpiryMonth: this.editForm.get(['cardExpiryMonth'])!.value,
      cardExpiryYear: this.editForm.get(['cardExpiryYear'])!.value,
      truncatedCardNumber: this.editForm.get(['truncatedCardNumber'])!.value,
    };
  }
}
