import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import dayjs from 'dayjs/esm';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';

import { IBitcoindLog, BitcoindLog } from '../bitcoind-log.model';
import { BitcoindLogService } from '../service/bitcoind-log.service';
import { ISiteUsers } from 'app/entities/site-users/site-users.model';
import { SiteUsersService } from 'app/entities/site-users/service/site-users.service';

@Component({
  selector: 'jhi-bitcoind-log-update',
  templateUrl: './bitcoind-log-update.component.html',
})
export class BitcoindLogUpdateComponent implements OnInit {
  isSaving = false;

  siteUsersCollection: ISiteUsers[] = [];

  editForm = this.fb.group({
    id: [],
    transactionId: [null, [Validators.required, Validators.maxLength(255)]],
    amount: [null, [Validators.required]],
    date: [null, [Validators.required]],
    siteUser: [],
  });

  constructor(
    protected bitcoindLogService: BitcoindLogService,
    protected siteUsersService: SiteUsersService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ bitcoindLog }) => {
      if (bitcoindLog.id === undefined) {
        const today = dayjs().startOf('day');
        bitcoindLog.date = today;
      }

      this.updateForm(bitcoindLog);

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const bitcoindLog = this.createFromForm();
    if (bitcoindLog.id !== undefined) {
      this.subscribeToSaveResponse(this.bitcoindLogService.update(bitcoindLog));
    } else {
      this.subscribeToSaveResponse(this.bitcoindLogService.create(bitcoindLog));
    }
  }

  trackSiteUsersById(_index: number, item: ISiteUsers): number {
    return item.id!;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IBitcoindLog>>): void {
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

  protected updateForm(bitcoindLog: IBitcoindLog): void {
    this.editForm.patchValue({
      id: bitcoindLog.id,
      transactionId: bitcoindLog.transactionId,
      amount: bitcoindLog.amount,
      date: bitcoindLog.date ? bitcoindLog.date.format(DATE_TIME_FORMAT) : null,
      siteUser: bitcoindLog.siteUser,
    });

    this.siteUsersCollection = this.siteUsersService.addSiteUsersToCollectionIfMissing(this.siteUsersCollection, bitcoindLog.siteUser);
  }

  protected loadRelationshipsOptions(): void {
    this.siteUsersService
      .query({ filter: 'bitcoindlog-is-null' })
      .pipe(map((res: HttpResponse<ISiteUsers[]>) => res.body ?? []))
      .pipe(
        map((siteUsers: ISiteUsers[]) =>
          this.siteUsersService.addSiteUsersToCollectionIfMissing(siteUsers, this.editForm.get('siteUser')!.value)
        )
      )
      .subscribe((siteUsers: ISiteUsers[]) => (this.siteUsersCollection = siteUsers));
  }

  protected createFromForm(): IBitcoindLog {
    return {
      ...new BitcoindLog(),
      id: this.editForm.get(['id'])!.value,
      transactionId: this.editForm.get(['transactionId'])!.value,
      amount: this.editForm.get(['amount'])!.value,
      date: this.editForm.get(['date'])!.value ? dayjs(this.editForm.get(['date'])!.value, DATE_TIME_FORMAT) : undefined,
      siteUser: this.editForm.get(['siteUser'])!.value,
    };
  }
}
