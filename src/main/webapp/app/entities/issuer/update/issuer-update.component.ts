import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { IIssuer, Issuer } from '../issuer.model';
import { IssuerService } from '../service/issuer.service';

@Component({
  selector: 'jhi-issuer-update',
  templateUrl: './issuer-update.component.html',
})
export class IssuerUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    name: [],
  });

  constructor(protected issuerService: IssuerService, protected activatedRoute: ActivatedRoute, protected fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ issuer }) => {
      this.updateForm(issuer);
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const issuer = this.createFromForm();
    if (issuer.id !== undefined) {
      this.subscribeToSaveResponse(this.issuerService.update(issuer));
    } else {
      this.subscribeToSaveResponse(this.issuerService.create(issuer));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IIssuer>>): void {
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

  protected updateForm(issuer: IIssuer): void {
    this.editForm.patchValue({
      id: issuer.id,
      name: issuer.name,
    });
  }

  protected createFromForm(): IIssuer {
    return {
      ...new Issuer(),
      id: this.editForm.get(['id'])!.value,
      name: this.editForm.get(['name'])!.value,
    };
  }
}
