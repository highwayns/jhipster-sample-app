import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IAdminGroups } from '../admin-groups.model';

@Component({
  selector: 'jhi-admin-groups-detail',
  templateUrl: './admin-groups-detail.component.html',
})
export class AdminGroupsDetailComponent implements OnInit {
  adminGroups: IAdminGroups | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ adminGroups }) => {
      this.adminGroups = adminGroups;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
