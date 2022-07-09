import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { ILinks, Links } from '../links.model';
import { LinksService } from '../service/links.service';
import { ILink } from 'app/entities/link/link.model';
import { LinkService } from 'app/entities/link/service/link.service';

@Component({
  selector: 'jhi-links-update',
  templateUrl: './links-update.component.html',
})
export class LinksUpdateComponent implements OnInit {
  isSaving = false;

  dataCollection: ILink[] = [];
  actionsCollection: ILink[] = [];
  documentationsCollection: ILink[] = [];

  editForm = this.fb.group({
    id: [],
    data: [],
    action: [],
    documentation: [],
  });

  constructor(
    protected linksService: LinksService,
    protected linkService: LinkService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ links }) => {
      this.updateForm(links);

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const links = this.createFromForm();
    if (links.id !== undefined) {
      this.subscribeToSaveResponse(this.linksService.update(links));
    } else {
      this.subscribeToSaveResponse(this.linksService.create(links));
    }
  }

  trackLinkById(_index: number, item: ILink): number {
    return item.id!;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ILinks>>): void {
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

  protected updateForm(links: ILinks): void {
    this.editForm.patchValue({
      id: links.id,
      data: links.data,
      action: links.action,
      documentation: links.documentation,
    });

    this.dataCollection = this.linkService.addLinkToCollectionIfMissing(this.dataCollection, links.data);
    this.actionsCollection = this.linkService.addLinkToCollectionIfMissing(this.actionsCollection, links.action);
    this.documentationsCollection = this.linkService.addLinkToCollectionIfMissing(this.documentationsCollection, links.documentation);
  }

  protected loadRelationshipsOptions(): void {
    this.linkService
      .query({ filter: 'links-is-null' })
      .pipe(map((res: HttpResponse<ILink[]>) => res.body ?? []))
      .pipe(map((links: ILink[]) => this.linkService.addLinkToCollectionIfMissing(links, this.editForm.get('data')!.value)))
      .subscribe((links: ILink[]) => (this.dataCollection = links));

    this.linkService
      .query({ filter: 'links-is-null' })
      .pipe(map((res: HttpResponse<ILink[]>) => res.body ?? []))
      .pipe(map((links: ILink[]) => this.linkService.addLinkToCollectionIfMissing(links, this.editForm.get('action')!.value)))
      .subscribe((links: ILink[]) => (this.actionsCollection = links));

    this.linkService
      .query({ filter: 'links-is-null' })
      .pipe(map((res: HttpResponse<ILink[]>) => res.body ?? []))
      .pipe(map((links: ILink[]) => this.linkService.addLinkToCollectionIfMissing(links, this.editForm.get('documentation')!.value)))
      .subscribe((links: ILink[]) => (this.documentationsCollection = links));
  }

  protected createFromForm(): ILinks {
    return {
      ...new Links(),
      id: this.editForm.get(['id'])!.value,
      data: this.editForm.get(['data'])!.value,
      action: this.editForm.get(['action'])!.value,
      documentation: this.editForm.get(['documentation'])!.value,
    };
  }
}
