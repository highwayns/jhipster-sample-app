import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IAdminGroupsPages } from '../admin-groups-pages.model';

@Component({
  selector: 'jhi-admin-groups-pages-detail',
  templateUrl: './admin-groups-pages-detail.component.html',
})
export class AdminGroupsPagesDetailComponent implements OnInit {
  adminGroupsPages: IAdminGroupsPages | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ adminGroupsPages }) => {
      this.adminGroupsPages = adminGroupsPages;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
