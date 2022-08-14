import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IAdminCron } from '../admin-cron.model';

@Component({
  selector: 'jhi-admin-cron-detail',
  templateUrl: './admin-cron-detail.component.html',
})
export class AdminCronDetailComponent implements OnInit {
  adminCron: IAdminCron | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ adminCron }) => {
      this.adminCron = adminCron;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
