import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IStatusEscrows } from '../status-escrows.model';

@Component({
  selector: 'jhi-status-escrows-detail',
  templateUrl: './status-escrows-detail.component.html',
})
export class StatusEscrowsDetailComponent implements OnInit {
  statusEscrows: IStatusEscrows | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ statusEscrows }) => {
      this.statusEscrows = statusEscrows;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
