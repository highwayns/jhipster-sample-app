import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IAdminGroupsTabs } from '../admin-groups-tabs.model';

@Component({
  selector: 'jhi-admin-groups-tabs-detail',
  templateUrl: './admin-groups-tabs-detail.component.html',
})
export class AdminGroupsTabsDetailComponent implements OnInit {
  adminGroupsTabs: IAdminGroupsTabs | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ adminGroupsTabs }) => {
      this.adminGroupsTabs = adminGroupsTabs;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
