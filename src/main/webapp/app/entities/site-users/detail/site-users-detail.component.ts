import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ISiteUsers } from '../site-users.model';

@Component({
  selector: 'jhi-site-users-detail',
  templateUrl: './site-users-detail.component.html',
})
export class SiteUsersDetailComponent implements OnInit {
  siteUsers: ISiteUsers | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ siteUsers }) => {
      this.siteUsers = siteUsers;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
