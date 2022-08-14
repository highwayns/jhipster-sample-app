import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import dayjs from 'dayjs/esm';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';

import { IStatus, Status } from '../status.model';
import { StatusService } from '../service/status.service';

@Component({
  selector: 'jhi-status-update',
  templateUrl: './status-update.component.html',
})
export class StatusUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    lastSweep: [null, [Validators.required]],
    deficitBtc: [null, [Validators.required]],
    hotWalletBtc: [null, [Validators.required]],
    warmWalletBtc: [null, [Validators.required]],
    totalBtc: [null, [Validators.required]],
    receivedBtcPending: [null, [Validators.required]],
    pendingWithdrawals: [null, [Validators.required]],
    tradingStatus: [null, [Validators.required, Validators.maxLength(255)]],
    withdrawalsStatus: [null, [Validators.required, Validators.maxLength(255)]],
    dbVersion: [null, [Validators.required]],
    cronDailyStats: [null, [Validators.required]],
    cronGetStats: [null, [Validators.required]],
    cronMaintenance: [null, [Validators.required]],
    cronMonthlyStats: [null, [Validators.required]],
    cronReceiveBitcoin: [null, [Validators.required]],
    cronSendBitcoin: [null, [Validators.required]],
  });

  constructor(protected statusService: StatusService, protected activatedRoute: ActivatedRoute, protected fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ status }) => {
      if (status.id === undefined) {
        const today = dayjs().startOf('day');
        status.lastSweep = today;
        status.cronDailyStats = today;
        status.cronGetStats = today;
        status.cronMaintenance = today;
        status.cronMonthlyStats = today;
        status.cronReceiveBitcoin = today;
        status.cronSendBitcoin = today;
      }

      this.updateForm(status);
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const status = this.createFromForm();
    if (status.id !== undefined) {
      this.subscribeToSaveResponse(this.statusService.update(status));
    } else {
      this.subscribeToSaveResponse(this.statusService.create(status));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IStatus>>): void {
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

  protected updateForm(status: IStatus): void {
    this.editForm.patchValue({
      id: status.id,
      lastSweep: status.lastSweep ? status.lastSweep.format(DATE_TIME_FORMAT) : null,
      deficitBtc: status.deficitBtc,
      hotWalletBtc: status.hotWalletBtc,
      warmWalletBtc: status.warmWalletBtc,
      totalBtc: status.totalBtc,
      receivedBtcPending: status.receivedBtcPending,
      pendingWithdrawals: status.pendingWithdrawals,
      tradingStatus: status.tradingStatus,
      withdrawalsStatus: status.withdrawalsStatus,
      dbVersion: status.dbVersion,
      cronDailyStats: status.cronDailyStats ? status.cronDailyStats.format(DATE_TIME_FORMAT) : null,
      cronGetStats: status.cronGetStats ? status.cronGetStats.format(DATE_TIME_FORMAT) : null,
      cronMaintenance: status.cronMaintenance ? status.cronMaintenance.format(DATE_TIME_FORMAT) : null,
      cronMonthlyStats: status.cronMonthlyStats ? status.cronMonthlyStats.format(DATE_TIME_FORMAT) : null,
      cronReceiveBitcoin: status.cronReceiveBitcoin ? status.cronReceiveBitcoin.format(DATE_TIME_FORMAT) : null,
      cronSendBitcoin: status.cronSendBitcoin ? status.cronSendBitcoin.format(DATE_TIME_FORMAT) : null,
    });
  }

  protected createFromForm(): IStatus {
    return {
      ...new Status(),
      id: this.editForm.get(['id'])!.value,
      lastSweep: this.editForm.get(['lastSweep'])!.value ? dayjs(this.editForm.get(['lastSweep'])!.value, DATE_TIME_FORMAT) : undefined,
      deficitBtc: this.editForm.get(['deficitBtc'])!.value,
      hotWalletBtc: this.editForm.get(['hotWalletBtc'])!.value,
      warmWalletBtc: this.editForm.get(['warmWalletBtc'])!.value,
      totalBtc: this.editForm.get(['totalBtc'])!.value,
      receivedBtcPending: this.editForm.get(['receivedBtcPending'])!.value,
      pendingWithdrawals: this.editForm.get(['pendingWithdrawals'])!.value,
      tradingStatus: this.editForm.get(['tradingStatus'])!.value,
      withdrawalsStatus: this.editForm.get(['withdrawalsStatus'])!.value,
      dbVersion: this.editForm.get(['dbVersion'])!.value,
      cronDailyStats: this.editForm.get(['cronDailyStats'])!.value
        ? dayjs(this.editForm.get(['cronDailyStats'])!.value, DATE_TIME_FORMAT)
        : undefined,
      cronGetStats: this.editForm.get(['cronGetStats'])!.value
        ? dayjs(this.editForm.get(['cronGetStats'])!.value, DATE_TIME_FORMAT)
        : undefined,
      cronMaintenance: this.editForm.get(['cronMaintenance'])!.value
        ? dayjs(this.editForm.get(['cronMaintenance'])!.value, DATE_TIME_FORMAT)
        : undefined,
      cronMonthlyStats: this.editForm.get(['cronMonthlyStats'])!.value
        ? dayjs(this.editForm.get(['cronMonthlyStats'])!.value, DATE_TIME_FORMAT)
        : undefined,
      cronReceiveBitcoin: this.editForm.get(['cronReceiveBitcoin'])!.value
        ? dayjs(this.editForm.get(['cronReceiveBitcoin'])!.value, DATE_TIME_FORMAT)
        : undefined,
      cronSendBitcoin: this.editForm.get(['cronSendBitcoin'])!.value
        ? dayjs(this.editForm.get(['cronSendBitcoin'])!.value, DATE_TIME_FORMAT)
        : undefined,
    };
  }
}
