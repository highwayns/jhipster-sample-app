import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ILang } from '../lang.model';

@Component({
  selector: 'jhi-lang-detail',
  templateUrl: './lang-detail.component.html',
})
export class LangDetailComponent implements OnInit {
  lang: ILang | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ lang }) => {
      this.lang = lang;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
