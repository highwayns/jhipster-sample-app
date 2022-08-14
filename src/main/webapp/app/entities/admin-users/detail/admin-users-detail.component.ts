import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IAdminUsers } from '../admin-users.model';

@Component({
  selector: 'jhi-admin-users-detail',
  templateUrl: './admin-users-detail.component.html',
})
export class AdminUsersDetailComponent implements OnInit {
  adminUsers: IAdminUsers | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ adminUsers }) => {
      this.adminUsers = adminUsers;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
