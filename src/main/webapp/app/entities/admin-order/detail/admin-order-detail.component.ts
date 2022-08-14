import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IAdminOrder } from '../admin-order.model';

@Component({
  selector: 'jhi-admin-order-detail',
  templateUrl: './admin-order-detail.component.html',
})
export class AdminOrderDetailComponent implements OnInit {
  adminOrder: IAdminOrder | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ adminOrder }) => {
      this.adminOrder = adminOrder;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
