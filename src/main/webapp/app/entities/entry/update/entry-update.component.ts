import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { IEntry, Entry } from '../entry.model';
import { EntryService } from '../service/entry.service';
import { IParameters } from 'app/entities/parameters/parameters.model';
import { ParametersService } from 'app/entities/parameters/service/parameters.service';

@Component({
  selector: 'jhi-entry-update',
  templateUrl: './entry-update.component.html',
})
export class EntryUpdateComponent implements OnInit {
  isSaving = false;

  parametersSharedCollection: IParameters[] = [];

  editForm = this.fb.group({
    id: [],
    code: [],
    message: [],
    translatedMessage: [],
    parameters: [],
  });

  constructor(
    protected entryService: EntryService,
    protected parametersService: ParametersService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ entry }) => {
      this.updateForm(entry);

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const entry = this.createFromForm();
    if (entry.id !== undefined) {
      this.subscribeToSaveResponse(this.entryService.update(entry));
    } else {
      this.subscribeToSaveResponse(this.entryService.create(entry));
    }
  }

  trackParametersById(_index: number, item: IParameters): number {
    return item.id!;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IEntry>>): void {
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

  protected updateForm(entry: IEntry): void {
    this.editForm.patchValue({
      id: entry.id,
      code: entry.code,
      message: entry.message,
      translatedMessage: entry.translatedMessage,
      parameters: entry.parameters,
    });

    this.parametersSharedCollection = this.parametersService.addParametersToCollectionIfMissing(
      this.parametersSharedCollection,
      entry.parameters
    );
  }

  protected loadRelationshipsOptions(): void {
    this.parametersService
      .query()
      .pipe(map((res: HttpResponse<IParameters[]>) => res.body ?? []))
      .pipe(
        map((parameters: IParameters[]) =>
          this.parametersService.addParametersToCollectionIfMissing(parameters, this.editForm.get('parameters')!.value)
        )
      )
      .subscribe((parameters: IParameters[]) => (this.parametersSharedCollection = parameters));
  }

  protected createFromForm(): IEntry {
    return {
      ...new Entry(),
      id: this.editForm.get(['id'])!.value,
      code: this.editForm.get(['code'])!.value,
      message: this.editForm.get(['message'])!.value,
      translatedMessage: this.editForm.get(['translatedMessage'])!.value,
      parameters: this.editForm.get(['parameters'])!.value,
    };
  }
}
