import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IOrderTypes } from '../order-types.model';

@Component({
  selector: 'jhi-order-types-detail',
  templateUrl: './order-types-detail.component.html',
})
export class OrderTypesDetailComponent implements OnInit {
  orderTypes: IOrderTypes | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ orderTypes }) => {
      this.orderTypes = orderTypes;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
