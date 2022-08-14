import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IContentFiles } from '../content-files.model';

@Component({
  selector: 'jhi-content-files-detail',
  templateUrl: './content-files-detail.component.html',
})
export class ContentFilesDetailComponent implements OnInit {
  contentFiles: IContentFiles | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ contentFiles }) => {
      this.contentFiles = contentFiles;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
