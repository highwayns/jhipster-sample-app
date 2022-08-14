import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { IEmails, Emails } from '../emails.model';
import { EmailsService } from '../service/emails.service';
import { AlertError } from 'app/shared/alert/alert-error.model';
import { EventManager, EventWithContent } from 'app/core/util/event-manager.service';
import { DataUtils, FileLoadError } from 'app/core/util/data-util.service';

@Component({
  selector: 'jhi-emails-update',
  templateUrl: './emails-update.component.html',
})
export class EmailsUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    key: [null, [Validators.required, Validators.maxLength(255)]],
    titleEn: [null, [Validators.required, Validators.maxLength(255)]],
    titleEs: [null, [Validators.required, Validators.maxLength(255)]],
    contentEn: [null, [Validators.required]],
    contentEnContentType: [],
    contentEs: [null, [Validators.required]],
    contentEsContentType: [],
    titleRu: [null, [Validators.required, Validators.maxLength(255)]],
    titleZh: [null, [Validators.required, Validators.maxLength(255)]],
    contentRu: [null, [Validators.required]],
    contentRuContentType: [],
    contentZh: [null, [Validators.required]],
    contentZhContentType: [],
  });

  constructor(
    protected dataUtils: DataUtils,
    protected eventManager: EventManager,
    protected emailsService: EmailsService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ emails }) => {
      this.updateForm(emails);
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
    const emails = this.createFromForm();
    if (emails.id !== undefined) {
      this.subscribeToSaveResponse(this.emailsService.update(emails));
    } else {
      this.subscribeToSaveResponse(this.emailsService.create(emails));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IEmails>>): void {
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

  protected updateForm(emails: IEmails): void {
    this.editForm.patchValue({
      id: emails.id,
      key: emails.key,
      titleEn: emails.titleEn,
      titleEs: emails.titleEs,
      contentEn: emails.contentEn,
      contentEnContentType: emails.contentEnContentType,
      contentEs: emails.contentEs,
      contentEsContentType: emails.contentEsContentType,
      titleRu: emails.titleRu,
      titleZh: emails.titleZh,
      contentRu: emails.contentRu,
      contentRuContentType: emails.contentRuContentType,
      contentZh: emails.contentZh,
      contentZhContentType: emails.contentZhContentType,
    });
  }

  protected createFromForm(): IEmails {
    return {
      ...new Emails(),
      id: this.editForm.get(['id'])!.value,
      key: this.editForm.get(['key'])!.value,
      titleEn: this.editForm.get(['titleEn'])!.value,
      titleEs: this.editForm.get(['titleEs'])!.value,
      contentEnContentType: this.editForm.get(['contentEnContentType'])!.value,
      contentEn: this.editForm.get(['contentEn'])!.value,
      contentEsContentType: this.editForm.get(['contentEsContentType'])!.value,
      contentEs: this.editForm.get(['contentEs'])!.value,
      titleRu: this.editForm.get(['titleRu'])!.value,
      titleZh: this.editForm.get(['titleZh'])!.value,
      contentRuContentType: this.editForm.get(['contentRuContentType'])!.value,
      contentRu: this.editForm.get(['contentRu'])!.value,
      contentZhContentType: this.editForm.get(['contentZhContentType'])!.value,
      contentZh: this.editForm.get(['contentZh'])!.value,
    };
  }
}
