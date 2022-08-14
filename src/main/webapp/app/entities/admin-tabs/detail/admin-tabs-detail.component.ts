import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IAdminTabs } from '../admin-tabs.model';

@Component({
  selector: 'jhi-admin-tabs-detail',
  templateUrl: './admin-tabs-detail.component.html',
})
export class AdminTabsDetailComponent implements OnInit {
  adminTabs: IAdminTabs | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ adminTabs }) => {
      this.adminTabs = adminTabs;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
