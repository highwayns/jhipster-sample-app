import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ISiteUsersCatch } from '../site-users-catch.model';

@Component({
  selector: 'jhi-site-users-catch-detail',
  templateUrl: './site-users-catch-detail.component.html',
})
export class SiteUsersCatchDetailComponent implements OnInit {
  siteUsersCatch: ISiteUsersCatch | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ siteUsersCatch }) => {
      this.siteUsersCatch = siteUsersCatch;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
