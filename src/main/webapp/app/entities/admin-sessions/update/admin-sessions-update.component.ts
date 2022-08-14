import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import dayjs from 'dayjs/esm';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';

import { IAdminSessions, AdminSessions } from '../admin-sessions.model';
import { AdminSessionsService } from '../service/admin-sessions.service';

@Component({
  selector: 'jhi-admin-sessions-update',
  templateUrl: './admin-sessions-update.component.html',
})
export class AdminSessionsUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    sessionId: [null, [Validators.required, Validators.maxLength(32)]],
    sessionTime: [null, [Validators.required]],
    sessionStart: [null, [Validators.required]],
    sessionValue: [null, [Validators.required, Validators.maxLength(255)]],
    ipAddress: [null, [Validators.required, Validators.maxLength(16)]],
    userAgent: [null, [Validators.required, Validators.maxLength(255)]],
  });

  constructor(protected adminSessionsService: AdminSessionsService, protected activatedRoute: ActivatedRoute, protected fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ adminSessions }) => {
      if (adminSessions.id === undefined) {
        const today = dayjs().startOf('day');
        adminSessions.sessionTime = today;
        adminSessions.sessionStart = today;
      }

      this.updateForm(adminSessions);
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const adminSessions = this.createFromForm();
    if (adminSessions.id !== undefined) {
      this.subscribeToSaveResponse(this.adminSessionsService.update(adminSessions));
    } else {
      this.subscribeToSaveResponse(this.adminSessionsService.create(adminSessions));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IAdminSessions>>): void {
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

  protected updateForm(adminSessions: IAdminSessions): void {
    this.editForm.patchValue({
      id: adminSessions.id,
      sessionId: adminSessions.sessionId,
      sessionTime: adminSessions.sessionTime ? adminSessions.sessionTime.format(DATE_TIME_FORMAT) : null,
      sessionStart: adminSessions.sessionStart ? adminSessions.sessionStart.format(DATE_TIME_FORMAT) : null,
      sessionValue: adminSessions.sessionValue,
      ipAddress: adminSessions.ipAddress,
      userAgent: adminSessions.userAgent,
    });
  }

  protected createFromForm(): IAdminSessions {
    return {
      ...new AdminSessions(),
      id: this.editForm.get(['id'])!.value,
      sessionId: this.editForm.get(['sessionId'])!.value,
      sessionTime: this.editForm.get(['sessionTime'])!.value
        ? dayjs(this.editForm.get(['sessionTime'])!.value, DATE_TIME_FORMAT)
        : undefined,
      sessionStart: this.editForm.get(['sessionStart'])!.value
        ? dayjs(this.editForm.get(['sessionStart'])!.value, DATE_TIME_FORMAT)
        : undefined,
      sessionValue: this.editForm.get(['sessionValue'])!.value,
      ipAddress: this.editForm.get(['ipAddress'])!.value,
      userAgent: this.editForm.get(['userAgent'])!.value,
    };
  }
}
