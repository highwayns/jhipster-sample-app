import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IAdminControls } from '../admin-controls.model';

@Component({
  selector: 'jhi-admin-controls-detail',
  templateUrl: './admin-controls-detail.component.html',
})
export class AdminControlsDetailComponent implements OnInit {
  adminControls: IAdminControls | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ adminControls }) => {
      this.adminControls = adminControls;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
