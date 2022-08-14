import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IBitcoindLog } from '../bitcoind-log.model';

@Component({
  selector: 'jhi-bitcoind-log-detail',
  templateUrl: './bitcoind-log-detail.component.html',
})
export class BitcoindLogDetailComponent implements OnInit {
  bitcoindLog: IBitcoindLog | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ bitcoindLog }) => {
      this.bitcoindLog = bitcoindLog;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
