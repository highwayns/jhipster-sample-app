import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { ISettingsFiles, SettingsFiles } from '../settings-files.model';
import { SettingsFilesService } from '../service/settings-files.service';

@Component({
  selector: 'jhi-settings-files-update',
  templateUrl: './settings-files-update.component.html',
})
export class SettingsFilesUpdateComponent implements OnInit {
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

  constructor(protected settingsFilesService: SettingsFilesService, protected activatedRoute: ActivatedRoute, protected fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ settingsFiles }) => {
      this.updateForm(settingsFiles);
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const settingsFiles = this.createFromForm();
    if (settingsFiles.id !== undefined) {
      this.subscribeToSaveResponse(this.settingsFilesService.update(settingsFiles));
    } else {
      this.subscribeToSaveResponse(this.settingsFilesService.create(settingsFiles));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ISettingsFiles>>): void {
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

  protected updateForm(settingsFiles: ISettingsFiles): void {
    this.editForm.patchValue({
      id: settingsFiles.id,
      fId: settingsFiles.fId,
      ext: settingsFiles.ext,
      dir: settingsFiles.dir,
      url: settingsFiles.url,
      oldName: settingsFiles.oldName,
      fieldName: settingsFiles.fieldName,
    });
  }

  protected createFromForm(): ISettingsFiles {
    return {
      ...new SettingsFiles(),
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
