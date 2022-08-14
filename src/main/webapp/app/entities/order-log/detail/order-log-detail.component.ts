import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IOrderLog } from '../order-log.model';

@Component({
  selector: 'jhi-order-log-detail',
  templateUrl: './order-log-detail.component.html',
})
export class OrderLogDetailComponent implements OnInit {
  orderLog: IOrderLog | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ orderLog }) => {
      this.orderLog = orderLog;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
