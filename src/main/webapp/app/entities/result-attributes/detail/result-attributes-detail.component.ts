import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IResultAttributes } from '../result-attributes.model';

@Component({
  selector: 'jhi-result-attributes-detail',
  templateUrl: './result-attributes-detail.component.html',
})
export class ResultAttributesDetailComponent implements OnInit {
  resultAttributes: IResultAttributes | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ resultAttributes }) => {
      this.resultAttributes = resultAttributes;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
