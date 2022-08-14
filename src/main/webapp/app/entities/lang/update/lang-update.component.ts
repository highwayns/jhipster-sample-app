import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { ILang, Lang } from '../lang.model';
import { LangService } from '../service/lang.service';

@Component({
  selector: 'jhi-lang-update',
  templateUrl: './lang-update.component.html',
})
export class LangUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    key: [null, [Validators.required, Validators.maxLength(255)]],
    esp: [null, [Validators.required, Validators.maxLength(255)]],
    eng: [null, [Validators.required, Validators.maxLength(255)]],
    order: [null, [Validators.required, Validators.maxLength(255)]],
    pId: [null, [Validators.required]],
    zh: [null, [Validators.required, Validators.maxLength(255)]],
    ru: [null, [Validators.required, Validators.maxLength(255)]],
  });

  constructor(protected langService: LangService, protected activatedRoute: ActivatedRoute, protected fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ lang }) => {
      this.updateForm(lang);
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const lang = this.createFromForm();
    if (lang.id !== undefined) {
      this.subscribeToSaveResponse(this.langService.update(lang));
    } else {
      this.subscribeToSaveResponse(this.langService.create(lang));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ILang>>): void {
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

  protected updateForm(lang: ILang): void {
    this.editForm.patchValue({
      id: lang.id,
      key: lang.key,
      esp: lang.esp,
      eng: lang.eng,
      order: lang.order,
      pId: lang.pId,
      zh: lang.zh,
      ru: lang.ru,
    });
  }

  protected createFromForm(): ILang {
    return {
      ...new Lang(),
      id: this.editForm.get(['id'])!.value,
      key: this.editForm.get(['key'])!.value,
      esp: this.editForm.get(['esp'])!.value,
      eng: this.editForm.get(['eng'])!.value,
      order: this.editForm.get(['order'])!.value,
      pId: this.editForm.get(['pId'])!.value,
      zh: this.editForm.get(['zh'])!.value,
      ru: this.editForm.get(['ru'])!.value,
    };
  }
}
