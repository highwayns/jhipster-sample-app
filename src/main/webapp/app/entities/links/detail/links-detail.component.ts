import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ILinks } from '../links.model';

@Component({
  selector: 'jhi-links-detail',
  templateUrl: './links-detail.component.html',
})
export class LinksDetailComponent implements OnInit {
  links: ILinks | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ links }) => {
      this.links = links;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
