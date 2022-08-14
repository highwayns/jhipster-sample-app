import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ISiteUsersAccess } from '../site-users-access.model';

@Component({
  selector: 'jhi-site-users-access-detail',
  templateUrl: './site-users-access-detail.component.html',
})
export class SiteUsersAccessDetailComponent implements OnInit {
  siteUsersAccess: ISiteUsersAccess | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ siteUsersAccess }) => {
      this.siteUsersAccess = siteUsersAccess;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
