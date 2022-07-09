import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { IParameters, Parameters } from '../parameters.model';
import { ParametersService } from '../service/parameters.service';

@Component({
  selector: 'jhi-parameters-update',
  templateUrl: './parameters-update.component.html',
})
export class ParametersUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    parameter: [],
  });

  constructor(protected parametersService: ParametersService, protected activatedRoute: ActivatedRoute, protected fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ parameters }) => {
      this.updateForm(parameters);
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const parameters = this.createFromForm();
    if (parameters.id !== undefined) {
      this.subscribeToSaveResponse(this.parametersService.update(parameters));
    } else {
      this.subscribeToSaveResponse(this.parametersService.create(parameters));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IParameters>>): void {
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

  protected updateForm(parameters: IParameters): void {
    this.editForm.patchValue({
      id: parameters.id,
      parameter: parameters.parameter,
    });
  }

  protected createFromForm(): IParameters {
    return {
      ...new Parameters(),
      id: this.editForm.get(['id'])!.value,
      parameter: this.editForm.get(['parameter'])!.value,
    };
  }
}
