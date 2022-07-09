import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { IErrorReport, ErrorReport } from '../error-report.model';
import { ErrorReportService } from '../service/error-report.service';
import { IEntry } from 'app/entities/entry/entry.model';
import { EntryService } from 'app/entities/entry/service/entry.service';
import { Locale } from 'app/entities/enumerations/locale.model';

@Component({
  selector: 'jhi-error-report-update',
  templateUrl: './error-report-update.component.html',
})
export class ErrorReportUpdateComponent implements OnInit {
  isSaving = false;
  localeValues = Object.keys(Locale);

  entriesSharedCollection: IEntry[] = [];

  editForm = this.fb.group({
    id: [],
    language: [],
    isFatalError: [],
    errors: [],
    warnings: [],
  });

  constructor(
    protected errorReportService: ErrorReportService,
    protected entryService: EntryService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ errorReport }) => {
      this.updateForm(errorReport);

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const errorReport = this.createFromForm();
    if (errorReport.id !== undefined) {
      this.subscribeToSaveResponse(this.errorReportService.update(errorReport));
    } else {
      this.subscribeToSaveResponse(this.errorReportService.create(errorReport));
    }
  }

  trackEntryById(_index: number, item: IEntry): number {
    return item.id!;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IErrorReport>>): void {
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

  protected updateForm(errorReport: IErrorReport): void {
    this.editForm.patchValue({
      id: errorReport.id,
      language: errorReport.language,
      isFatalError: errorReport.isFatalError,
      errors: errorReport.errors,
      warnings: errorReport.warnings,
    });

    this.entriesSharedCollection = this.entryService.addEntryToCollectionIfMissing(
      this.entriesSharedCollection,
      errorReport.errors,
      errorReport.warnings
    );
  }

  protected loadRelationshipsOptions(): void {
    this.entryService
      .query()
      .pipe(map((res: HttpResponse<IEntry[]>) => res.body ?? []))
      .pipe(
        map((entries: IEntry[]) =>
          this.entryService.addEntryToCollectionIfMissing(entries, this.editForm.get('errors')!.value, this.editForm.get('warnings')!.value)
        )
      )
      .subscribe((entries: IEntry[]) => (this.entriesSharedCollection = entries));
  }

  protected createFromForm(): IErrorReport {
    return {
      ...new ErrorReport(),
      id: this.editForm.get(['id'])!.value,
      language: this.editForm.get(['language'])!.value,
      isFatalError: this.editForm.get(['isFatalError'])!.value,
      errors: this.editForm.get(['errors'])!.value,
      warnings: this.editForm.get(['warnings'])!.value,
    };
  }
}
