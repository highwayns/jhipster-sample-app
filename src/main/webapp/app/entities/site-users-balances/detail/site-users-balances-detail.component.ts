import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ISiteUsersBalances } from '../site-users-balances.model';

@Component({
  selector: 'jhi-site-users-balances-detail',
  templateUrl: './site-users-balances-detail.component.html',
})
export class SiteUsersBalancesDetailComponent implements OnInit {
  siteUsersBalances: ISiteUsersBalances | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ siteUsersBalances }) => {
      this.siteUsersBalances = siteUsersBalances;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
