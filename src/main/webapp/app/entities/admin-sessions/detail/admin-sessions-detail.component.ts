import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IAdminSessions } from '../admin-sessions.model';

@Component({
  selector: 'jhi-admin-sessions-detail',
  templateUrl: './admin-sessions-detail.component.html',
})
export class AdminSessionsDetailComponent implements OnInit {
  adminSessions: IAdminSessions | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ adminSessions }) => {
      this.adminSessions = adminSessions;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
