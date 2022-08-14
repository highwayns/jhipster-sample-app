import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IAdminControlsMethods } from '../admin-controls-methods.model';

@Component({
  selector: 'jhi-admin-controls-methods-detail',
  templateUrl: './admin-controls-methods-detail.component.html',
})
export class AdminControlsMethodsDetailComponent implements OnInit {
  adminControlsMethods: IAdminControlsMethods | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ adminControlsMethods }) => {
      this.adminControlsMethods = adminControlsMethods;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
