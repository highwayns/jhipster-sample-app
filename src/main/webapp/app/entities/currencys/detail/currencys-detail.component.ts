import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ICurrencys } from '../currencys.model';

@Component({
  selector: 'jhi-currencys-detail',
  templateUrl: './currencys-detail.component.html',
})
export class CurrencysDetailComponent implements OnInit {
  currencys: ICurrencys | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ currencys }) => {
      this.currencys = currencys;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
