import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import dayjs from 'dayjs/esm';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';

import { INews, News } from '../news.model';
import { NewsService } from '../service/news.service';
import { AlertError } from 'app/shared/alert/alert-error.model';
import { EventManager, EventWithContent } from 'app/core/util/event-manager.service';
import { DataUtils, FileLoadError } from 'app/core/util/data-util.service';

@Component({
  selector: 'jhi-news-update',
  templateUrl: './news-update.component.html',
})
export class NewsUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    titleEn: [null, [Validators.required, Validators.maxLength(255)]],
    titleEs: [null, [Validators.required, Validators.maxLength(255)]],
    date: [null, [Validators.required]],
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
    protected newsService: NewsService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ news }) => {
      if (news.id === undefined) {
        const today = dayjs().startOf('day');
        news.date = today;
      }

      this.updateForm(news);
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
    const news = this.createFromForm();
    if (news.id !== undefined) {
      this.subscribeToSaveResponse(this.newsService.update(news));
    } else {
      this.subscribeToSaveResponse(this.newsService.create(news));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<INews>>): void {
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

  protected updateForm(news: INews): void {
    this.editForm.patchValue({
      id: news.id,
      titleEn: news.titleEn,
      titleEs: news.titleEs,
      date: news.date ? news.date.format(DATE_TIME_FORMAT) : null,
      contentEn: news.contentEn,
      contentEnContentType: news.contentEnContentType,
      contentEs: news.contentEs,
      contentEsContentType: news.contentEsContentType,
      titleRu: news.titleRu,
      titleZh: news.titleZh,
      contentRu: news.contentRu,
      contentRuContentType: news.contentRuContentType,
      contentZh: news.contentZh,
      contentZhContentType: news.contentZhContentType,
    });
  }

  protected createFromForm(): INews {
    return {
      ...new News(),
      id: this.editForm.get(['id'])!.value,
      titleEn: this.editForm.get(['titleEn'])!.value,
      titleEs: this.editForm.get(['titleEs'])!.value,
      date: this.editForm.get(['date'])!.value ? dayjs(this.editForm.get(['date'])!.value, DATE_TIME_FORMAT) : undefined,
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
