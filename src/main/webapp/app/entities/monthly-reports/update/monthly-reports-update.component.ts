import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import dayjs from 'dayjs/esm';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';

import { IMonthlyReports, MonthlyReports } from '../monthly-reports.model';
import { MonthlyReportsService } from '../service/monthly-reports.service';

@Component({
  selector: 'jhi-monthly-reports-update',
  templateUrl: './monthly-reports-update.component.html',
})
export class MonthlyReportsUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    date: [null, [Validators.required]],
    transactionsBtc: [null, [Validators.required]],
    avgTransactionSizeBtc: [null, [Validators.required]],
    transactionVolumePerUser: [null, [Validators.required]],
    totalFeesBtc: [null, [Validators.required]],
    feesPerUserBtc: [null, [Validators.required]],
    grossProfitBtc: [null, [Validators.required]],
  });

  constructor(
    protected monthlyReportsService: MonthlyReportsService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ monthlyReports }) => {
      if (monthlyReports.id === undefined) {
        const today = dayjs().startOf('day');
        monthlyReports.date = today;
      }

      this.updateForm(monthlyReports);
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const monthlyReports = this.createFromForm();
    if (monthlyReports.id !== undefined) {
      this.subscribeToSaveResponse(this.monthlyReportsService.update(monthlyReports));
    } else {
      this.subscribeToSaveResponse(this.monthlyReportsService.create(monthlyReports));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IMonthlyReports>>): void {
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

  protected updateForm(monthlyReports: IMonthlyReports): void {
    this.editForm.patchValue({
      id: monthlyReports.id,
      date: monthlyReports.date ? monthlyReports.date.format(DATE_TIME_FORMAT) : null,
      transactionsBtc: monthlyReports.transactionsBtc,
      avgTransactionSizeBtc: monthlyReports.avgTransactionSizeBtc,
      transactionVolumePerUser: monthlyReports.transactionVolumePerUser,
      totalFeesBtc: monthlyReports.totalFeesBtc,
      feesPerUserBtc: monthlyReports.feesPerUserBtc,
      grossProfitBtc: monthlyReports.grossProfitBtc,
    });
  }

  protected createFromForm(): IMonthlyReports {
    return {
      ...new MonthlyReports(),
      id: this.editForm.get(['id'])!.value,
      date: this.editForm.get(['date'])!.value ? dayjs(this.editForm.get(['date'])!.value, DATE_TIME_FORMAT) : undefined,
      transactionsBtc: this.editForm.get(['transactionsBtc'])!.value,
      avgTransactionSizeBtc: this.editForm.get(['avgTransactionSizeBtc'])!.value,
      transactionVolumePerUser: this.editForm.get(['transactionVolumePerUser'])!.value,
      totalFeesBtc: this.editForm.get(['totalFeesBtc'])!.value,
      feesPerUserBtc: this.editForm.get(['feesPerUserBtc'])!.value,
      grossProfitBtc: this.editForm.get(['grossProfitBtc'])!.value,
    };
  }
}
