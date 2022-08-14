import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import dayjs from 'dayjs/esm';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';

import { IChangeSettings, ChangeSettings } from '../change-settings.model';
import { ChangeSettingsService } from '../service/change-settings.service';
import { AlertError } from 'app/shared/alert/alert-error.model';
import { EventManager, EventWithContent } from 'app/core/util/event-manager.service';
import { DataUtils, FileLoadError } from 'app/core/util/data-util.service';
import { ISiteUsers } from 'app/entities/site-users/site-users.model';
import { SiteUsersService } from 'app/entities/site-users/service/site-users.service';

@Component({
  selector: 'jhi-change-settings-update',
  templateUrl: './change-settings-update.component.html',
})
export class ChangeSettingsUpdateComponent implements OnInit {
  isSaving = false;

  siteUsersCollection: ISiteUsers[] = [];

  editForm = this.fb.group({
    id: [],
    request: [null, [Validators.required]],
    requestContentType: [],
    date: [null, [Validators.required]],
    siteUser: [],
  });

  constructor(
    protected dataUtils: DataUtils,
    protected eventManager: EventManager,
    protected changeSettingsService: ChangeSettingsService,
    protected siteUsersService: SiteUsersService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ changeSettings }) => {
      if (changeSettings.id === undefined) {
        const today = dayjs().startOf('day');
        changeSettings.date = today;
      }

      this.updateForm(changeSettings);

      this.loadRelationshipsOptions();
    });
  }

  byteSize(base64String: string): string {
    return this.dataUtils.byteSize(base64String);
  }

  openFile(base64String: string, contentType: string | null | undefined): void {
    this.dataUtils.openFile(base64String, contentType);
  }

  setFileData(event: Event, field: string, isImage: boolean): void {
    this.dataUtils.loadFileToForm(event, this.editForm, field, isImage).subscribe({
      error: (err: FileLoadError) =>
        this.eventManager.broadcast(
          new EventWithContent<AlertError>('jhipsterSampleApplicationApp.error', { ...err, key: 'error.file.' + err.key })
        ),
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const changeSettings = this.createFromForm();
    if (changeSettings.id !== undefined) {
      this.subscribeToSaveResponse(this.changeSettingsService.update(changeSettings));
    } else {
      this.subscribeToSaveResponse(this.changeSettingsService.create(changeSettings));
    }
  }

  trackSiteUsersById(_index: number, item: ISiteUsers): number {
    return item.id!;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IChangeSettings>>): void {
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

  protected updateForm(changeSettings: IChangeSettings): void {
    this.editForm.patchValue({
      id: changeSettings.id,
      request: changeSettings.request,
      requestContentType: changeSettings.requestContentType,
      date: changeSettings.date ? changeSettings.date.format(DATE_TIME_FORMAT) : null,
      siteUser: changeSettings.siteUser,
    });

    this.siteUsersCollection = this.siteUsersService.addSiteUsersToCollectionIfMissing(this.siteUsersCollection, changeSettings.siteUser);
  }

  protected loadRelationshipsOptions(): void {
    this.siteUsersService
      .query({ filter: 'changesettings-is-null' })
      .pipe(map((res: HttpResponse<ISiteUsers[]>) => res.body ?? []))
      .pipe(
        map((siteUsers: ISiteUsers[]) =>
          this.siteUsersService.addSiteUsersToCollectionIfMissing(siteUsers, this.editForm.get('siteUser')!.value)
        )
      )
      .subscribe((siteUsers: ISiteUsers[]) => (this.siteUsersCollection = siteUsers));
  }

  protected createFromForm(): IChangeSettings {
    return {
      ...new ChangeSettings(),
      id: this.editForm.get(['id'])!.value,
      requestContentType: this.editForm.get(['requestContentType'])!.value,
      request: this.editForm.get(['request'])!.value,
      date: this.editForm.get(['date'])!.value ? dayjs(this.editForm.get(['date'])!.value, DATE_TIME_FORMAT) : undefined,
      siteUser: this.editForm.get(['siteUser'])!.value,
    };
  }
}
