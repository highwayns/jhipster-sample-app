import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { IIsoCountries, IsoCountries } from '../iso-countries.model';
import { IsoCountriesService } from '../service/iso-countries.service';

@Component({
  selector: 'jhi-iso-countries-update',
  templateUrl: './iso-countries-update.component.html',
})
export class IsoCountriesUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    locale: [null, [Validators.required, Validators.maxLength(10)]],
    code: [null, [Validators.required, Validators.maxLength(2)]],
    name: [null, [Validators.maxLength(200)]],
    prefix: [null, [Validators.maxLength(50)]],
  });

  constructor(protected isoCountriesService: IsoCountriesService, protected activatedRoute: ActivatedRoute, protected fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ isoCountries }) => {
      this.updateForm(isoCountries);
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const isoCountries = this.createFromForm();
    if (isoCountries.id !== undefined) {
      this.subscribeToSaveResponse(this.isoCountriesService.update(isoCountries));
    } else {
      this.subscribeToSaveResponse(this.isoCountriesService.create(isoCountries));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IIsoCountries>>): void {
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

  protected updateForm(isoCountries: IIsoCountries): void {
    this.editForm.patchValue({
      id: isoCountries.id,
      locale: isoCountries.locale,
      code: isoCountries.code,
      name: isoCountries.name,
      prefix: isoCountries.prefix,
    });
  }

  protected createFromForm(): IIsoCountries {
    return {
      ...new IsoCountries(),
      id: this.editForm.get(['id'])!.value,
      locale: this.editForm.get(['locale'])!.value,
      code: this.editForm.get(['code'])!.value,
      name: this.editForm.get(['name'])!.value,
      prefix: this.editForm.get(['prefix'])!.value,
    };
  }
}
