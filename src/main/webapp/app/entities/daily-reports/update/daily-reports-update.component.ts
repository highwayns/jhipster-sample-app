import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import dayjs from 'dayjs/esm';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';

import { IDailyReports, DailyReports } from '../daily-reports.model';
import { DailyReportsService } from '../service/daily-reports.service';

@Component({
  selector: 'jhi-daily-reports-update',
  templateUrl: './daily-reports-update.component.html',
})
export class DailyReportsUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    date: [null, [Validators.required]],
    totalBtc: [null, [Validators.required]],
    totalFiatUsd: [null, [Validators.required]],
    openOrdersBtc: [null, [Validators.required]],
    btcPerUser: [null, [Validators.required]],
    transactionsBtc: [null, [Validators.required]],
    avgTransactionSizeBtc: [null, [Validators.required]],
    transactionVolumePerUser: [null, [Validators.required]],
    totalFeesBtc: [null, [Validators.required]],
    feesPerUserBtc: [null, [Validators.required]],
    usdPerUser: [null, [Validators.required]],
    grossProfitBtc: [null, [Validators.required]],
  });

  constructor(protected dailyReportsService: DailyReportsService, protected activatedRoute: ActivatedRoute, protected fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ dailyReports }) => {
      if (dailyReports.id === undefined) {
        const today = dayjs().startOf('day');
        dailyReports.date = today;
      }

      this.updateForm(dailyReports);
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const dailyReports = this.createFromForm();
    if (dailyReports.id !== undefined) {
      this.subscribeToSaveResponse(this.dailyReportsService.update(dailyReports));
    } else {
      this.subscribeToSaveResponse(this.dailyReportsService.create(dailyReports));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IDailyReports>>): void {
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

  protected updateForm(dailyReports: IDailyReports): void {
    this.editForm.patchValue({
      id: dailyReports.id,
      date: dailyReports.date ? dailyReports.date.format(DATE_TIME_FORMAT) : null,
      totalBtc: dailyReports.totalBtc,
      totalFiatUsd: dailyReports.totalFiatUsd,
      openOrdersBtc: dailyReports.openOrdersBtc,
      btcPerUser: dailyReports.btcPerUser,
      transactionsBtc: dailyReports.transactionsBtc,
      avgTransactionSizeBtc: dailyReports.avgTransactionSizeBtc,
      transactionVolumePerUser: dailyReports.transactionVolumePerUser,
      totalFeesBtc: dailyReports.totalFeesBtc,
      feesPerUserBtc: dailyReports.feesPerUserBtc,
      usdPerUser: dailyReports.usdPerUser,
      grossProfitBtc: dailyReports.grossProfitBtc,
    });
  }

  protected createFromForm(): IDailyReports {
    return {
      ...new DailyReports(),
      id: this.editForm.get(['id'])!.value,
      date: this.editForm.get(['date'])!.value ? dayjs(this.editForm.get(['date'])!.value, DATE_TIME_FORMAT) : undefined,
      totalBtc: this.editForm.get(['totalBtc'])!.value,
      totalFiatUsd: this.editForm.get(['totalFiatUsd'])!.value,
      openOrdersBtc: this.editForm.get(['openOrdersBtc'])!.value,
      btcPerUser: this.editForm.get(['btcPerUser'])!.value,
      transactionsBtc: this.editForm.get(['transactionsBtc'])!.value,
      avgTransactionSizeBtc: this.editForm.get(['avgTransactionSizeBtc'])!.value,
      transactionVolumePerUser: this.editForm.get(['transactionVolumePerUser'])!.value,
      totalFeesBtc: this.editForm.get(['totalFeesBtc'])!.value,
      feesPerUserBtc: this.editForm.get(['feesPerUserBtc'])!.value,
      usdPerUser: this.editForm.get(['usdPerUser'])!.value,
      grossProfitBtc: this.editForm.get(['grossProfitBtc'])!.value,
    };
  }
}
