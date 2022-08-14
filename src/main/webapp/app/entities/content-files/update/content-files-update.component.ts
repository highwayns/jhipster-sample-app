import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { IContentFiles, ContentFiles } from '../content-files.model';
import { ContentFilesService } from '../service/content-files.service';

@Component({
  selector: 'jhi-content-files-update',
  templateUrl: './content-files-update.component.html',
})
export class ContentFilesUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    fId: [null, [Validators.required]],
    ext: [null, [Validators.required, Validators.maxLength(4)]],
    dir: [null, [Validators.required, Validators.maxLength(255)]],
    url: [null, [Validators.required, Validators.maxLength(255)]],
    oldName: [null, [Validators.required, Validators.maxLength(255)]],
    fieldName: [null, [Validators.required, Validators.maxLength(50)]],
  });

  constructor(protected contentFilesService: ContentFilesService, protected activatedRoute: ActivatedRoute, protected fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ contentFiles }) => {
      this.updateForm(contentFiles);
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const contentFiles = this.createFromForm();
    if (contentFiles.id !== undefined) {
      this.subscribeToSaveResponse(this.contentFilesService.update(contentFiles));
    } else {
      this.subscribeToSaveResponse(this.contentFilesService.create(contentFiles));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IContentFiles>>): void {
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

  protected updateForm(contentFiles: IContentFiles): void {
    this.editForm.patchValue({
      id: contentFiles.id,
      fId: contentFiles.fId,
      ext: contentFiles.ext,
      dir: contentFiles.dir,
      url: contentFiles.url,
      oldName: contentFiles.oldName,
      fieldName: contentFiles.fieldName,
    });
  }

  protected createFromForm(): IContentFiles {
    return {
      ...new ContentFiles(),
      id: this.editForm.get(['id'])!.value,
      fId: this.editForm.get(['fId'])!.value,
      ext: this.editForm.get(['ext'])!.value,
      dir: this.editForm.get(['dir'])!.value,
      url: this.editForm.get(['url'])!.value,
      oldName: this.editForm.get(['oldName'])!.value,
      fieldName: this.editForm.get(['fieldName'])!.value,
    };
  }
}
