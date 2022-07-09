import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import dayjs from 'dayjs/esm';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';

import { IAbuseReport, AbuseReport } from '../abuse-report.model';
import { AbuseReportService } from '../service/abuse-report.service';

@Component({
  selector: 'jhi-abuse-report-update',
  templateUrl: './abuse-report-update.component.html',
})
export class AbuseReportUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    score: [],
    createdDateTimeUtc: [],
  });

  constructor(protected abuseReportService: AbuseReportService, protected activatedRoute: ActivatedRoute, protected fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ abuseReport }) => {
      if (abuseReport.id === undefined) {
        const today = dayjs().startOf('day');
        abuseReport.createdDateTimeUtc = today;
      }

      this.updateForm(abuseReport);
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const abuseReport = this.createFromForm();
    if (abuseReport.id !== undefined) {
      this.subscribeToSaveResponse(this.abuseReportService.update(abuseReport));
    } else {
      this.subscribeToSaveResponse(this.abuseReportService.create(abuseReport));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IAbuseReport>>): void {
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

  protected updateForm(abuseReport: IAbuseReport): void {
    this.editForm.patchValue({
      id: abuseReport.id,
      score: abuseReport.score,
      createdDateTimeUtc: abuseReport.createdDateTimeUtc ? abuseReport.createdDateTimeUtc.format(DATE_TIME_FORMAT) : null,
    });
  }

  protected createFromForm(): IAbuseReport {
    return {
      ...new AbuseReport(),
      id: this.editForm.get(['id'])!.value,
      score: this.editForm.get(['score'])!.value,
      createdDateTimeUtc: this.editForm.get(['createdDateTimeUtc'])!.value
        ? dayjs(this.editForm.get(['createdDateTimeUtc'])!.value, DATE_TIME_FORMAT)
        : undefined,
    };
  }
}
