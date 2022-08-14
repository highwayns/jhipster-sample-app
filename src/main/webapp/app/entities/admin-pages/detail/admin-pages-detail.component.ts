import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IAdminPages } from '../admin-pages.model';

@Component({
  selector: 'jhi-admin-pages-detail',
  templateUrl: './admin-pages-detail.component.html',
})
export class AdminPagesDetailComponent implements OnInit {
  adminPages: IAdminPages | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ adminPages }) => {
      this.adminPages = adminPages;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
