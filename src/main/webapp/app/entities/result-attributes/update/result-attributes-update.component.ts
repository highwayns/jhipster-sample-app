import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { IResultAttributes, ResultAttributes } from '../result-attributes.model';
import { ResultAttributesService } from '../service/result-attributes.service';

@Component({
  selector: 'jhi-result-attributes-update',
  templateUrl: './result-attributes-update.component.html',
})
export class ResultAttributesUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    key: [],
    value: [],
  });

  constructor(
    protected resultAttributesService: ResultAttributesService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ resultAttributes }) => {
      this.updateForm(resultAttributes);
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const resultAttributes = this.createFromForm();
    if (resultAttributes.id !== undefined) {
      this.subscribeToSaveResponse(this.resultAttributesService.update(resultAttributes));
    } else {
      this.subscribeToSaveResponse(this.resultAttributesService.create(resultAttributes));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IResultAttributes>>): void {
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

  protected updateForm(resultAttributes: IResultAttributes): void {
    this.editForm.patchValue({
      id: resultAttributes.id,
      key: resultAttributes.key,
      value: resultAttributes.value,
    });
  }

  protected createFromForm(): IResultAttributes {
    return {
      ...new ResultAttributes(),
      id: this.editForm.get(['id'])!.value,
      key: this.editForm.get(['key'])!.value,
      value: this.editForm.get(['value'])!.value,
    };
  }
}
