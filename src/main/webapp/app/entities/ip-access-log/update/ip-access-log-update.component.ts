import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import dayjs from 'dayjs/esm';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';

import { IIpAccessLog, IpAccessLog } from '../ip-access-log.model';
import { IpAccessLogService } from '../service/ip-access-log.service';
import { YesNo } from 'app/entities/enumerations/yes-no.model';

@Component({
  selector: 'jhi-ip-access-log-update',
  templateUrl: './ip-access-log-update.component.html',
})
export class IpAccessLogUpdateComponent implements OnInit {
  isSaving = false;
  yesNoValues = Object.keys(YesNo);

  editForm = this.fb.group({
    id: [],
    ip: [null, [Validators.required]],
    timestamp: [null, [Validators.required]],
    login: [null, [Validators.required]],
  });

  constructor(protected ipAccessLogService: IpAccessLogService, protected activatedRoute: ActivatedRoute, protected fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ ipAccessLog }) => {
      if (ipAccessLog.id === undefined) {
        const today = dayjs().startOf('day');
        ipAccessLog.timestamp = today;
      }

      this.updateForm(ipAccessLog);
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const ipAccessLog = this.createFromForm();
    if (ipAccessLog.id !== undefined) {
      this.subscribeToSaveResponse(this.ipAccessLogService.update(ipAccessLog));
    } else {
      this.subscribeToSaveResponse(this.ipAccessLogService.create(ipAccessLog));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IIpAccessLog>>): void {
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

  protected updateForm(ipAccessLog: IIpAccessLog): void {
    this.editForm.patchValue({
      id: ipAccessLog.id,
      ip: ipAccessLog.ip,
      timestamp: ipAccessLog.timestamp ? ipAccessLog.timestamp.format(DATE_TIME_FORMAT) : null,
      login: ipAccessLog.login,
    });
  }

  protected createFromForm(): IIpAccessLog {
    return {
      ...new IpAccessLog(),
      id: this.editForm.get(['id'])!.value,
      ip: this.editForm.get(['ip'])!.value,
      timestamp: this.editForm.get(['timestamp'])!.value ? dayjs(this.editForm.get(['timestamp'])!.value, DATE_TIME_FORMAT) : undefined,
      login: this.editForm.get(['login'])!.value,
    };
  }
}
