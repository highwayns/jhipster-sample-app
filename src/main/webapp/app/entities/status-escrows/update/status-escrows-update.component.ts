import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { IStatusEscrows, StatusEscrows } from '../status-escrows.model';
import { StatusEscrowsService } from '../service/status-escrows.service';
import { ICurrencies } from 'app/entities/currencies/currencies.model';
import { CurrenciesService } from 'app/entities/currencies/service/currencies.service';
import { IStatus } from 'app/entities/status/status.model';
import { StatusService } from 'app/entities/status/service/status.service';

@Component({
  selector: 'jhi-status-escrows-update',
  templateUrl: './status-escrows-update.component.html',
})
export class StatusEscrowsUpdateComponent implements OnInit {
  isSaving = false;

  currenciesCollection: ICurrencies[] = [];
  statusIdsCollection: IStatus[] = [];

  editForm = this.fb.group({
    id: [],
    balance: [null, [Validators.required]],
    currency: [],
    statusId: [],
  });

  constructor(
    protected statusEscrowsService: StatusEscrowsService,
    protected currenciesService: CurrenciesService,
    protected statusService: StatusService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ statusEscrows }) => {
      this.updateForm(statusEscrows);

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const statusEscrows = this.createFromForm();
    if (statusEscrows.id !== undefined) {
      this.subscribeToSaveResponse(this.statusEscrowsService.update(statusEscrows));
    } else {
      this.subscribeToSaveResponse(this.statusEscrowsService.create(statusEscrows));
    }
  }

  trackCurrenciesById(_index: number, item: ICurrencies): number {
    return item.id!;
  }

  trackStatusById(_index: number, item: IStatus): number {
    return item.id!;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IStatusEscrows>>): void {
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

  protected updateForm(statusEscrows: IStatusEscrows): void {
    this.editForm.patchValue({
      id: statusEscrows.id,
      balance: statusEscrows.balance,
      currency: statusEscrows.currency,
      statusId: statusEscrows.statusId,
    });

    this.currenciesCollection = this.currenciesService.addCurrenciesToCollectionIfMissing(
      this.currenciesCollection,
      statusEscrows.currency
    );
    this.statusIdsCollection = this.statusService.addStatusToCollectionIfMissing(this.statusIdsCollection, statusEscrows.statusId);
  }

  protected loadRelationshipsOptions(): void {
    this.currenciesService
      .query({ filter: 'statusescrows-is-null' })
      .pipe(map((res: HttpResponse<ICurrencies[]>) => res.body ?? []))
      .pipe(
        map((currencies: ICurrencies[]) =>
          this.currenciesService.addCurrenciesToCollectionIfMissing(currencies, this.editForm.get('currency')!.value)
        )
      )
      .subscribe((currencies: ICurrencies[]) => (this.currenciesCollection = currencies));

    this.statusService
      .query({ filter: 'statusescrows-is-null' })
      .pipe(map((res: HttpResponse<IStatus[]>) => res.body ?? []))
      .pipe(map((statuses: IStatus[]) => this.statusService.addStatusToCollectionIfMissing(statuses, this.editForm.get('statusId')!.value)))
      .subscribe((statuses: IStatus[]) => (this.statusIdsCollection = statuses));
  }

  protected createFromForm(): IStatusEscrows {
    return {
      ...new StatusEscrows(),
      id: this.editForm.get(['id'])!.value,
      balance: this.editForm.get(['balance'])!.value,
      currency: this.editForm.get(['currency'])!.value,
      statusId: this.editForm.get(['statusId'])!.value,
    };
  }
}
