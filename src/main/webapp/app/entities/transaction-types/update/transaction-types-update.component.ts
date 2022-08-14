import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { ITransactionTypes, TransactionTypes } from '../transaction-types.model';
import { TransactionTypesService } from '../service/transaction-types.service';

@Component({
  selector: 'jhi-transaction-types-update',
  templateUrl: './transaction-types-update.component.html',
})
export class TransactionTypesUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    nameEn: [null, [Validators.required, Validators.maxLength(255)]],
    nameEs: [null, [Validators.required, Validators.maxLength(255)]],
    nameRu: [null, [Validators.required, Validators.maxLength(255)]],
    nameZh: [null, [Validators.required, Validators.maxLength(255)]],
  });

  constructor(
    protected transactionTypesService: TransactionTypesService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ transactionTypes }) => {
      this.updateForm(transactionTypes);
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const transactionTypes = this.createFromForm();
    if (transactionTypes.id !== undefined) {
      this.subscribeToSaveResponse(this.transactionTypesService.update(transactionTypes));
    } else {
      this.subscribeToSaveResponse(this.transactionTypesService.create(transactionTypes));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ITransactionTypes>>): void {
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

  protected updateForm(transactionTypes: ITransactionTypes): void {
    this.editForm.patchValue({
      id: transactionTypes.id,
      nameEn: transactionTypes.nameEn,
      nameEs: transactionTypes.nameEs,
      nameRu: transactionTypes.nameRu,
      nameZh: transactionTypes.nameZh,
    });
  }

  protected createFromForm(): ITransactionTypes {
    return {
      ...new TransactionTypes(),
      id: this.editForm.get(['id'])!.value,
      nameEn: this.editForm.get(['nameEn'])!.value,
      nameEs: this.editForm.get(['nameEs'])!.value,
      nameRu: this.editForm.get(['nameRu'])!.value,
      nameZh: this.editForm.get(['nameZh'])!.value,
    };
  }
}
