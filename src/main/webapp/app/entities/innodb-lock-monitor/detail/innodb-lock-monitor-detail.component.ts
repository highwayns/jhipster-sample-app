import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IInnodbLockMonitor } from '../innodb-lock-monitor.model';

@Component({
  selector: 'jhi-innodb-lock-monitor-detail',
  templateUrl: './innodb-lock-monitor-detail.component.html',
})
export class InnodbLockMonitorDetailComponent implements OnInit {
  innodbLockMonitor: IInnodbLockMonitor | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ innodbLockMonitor }) => {
      this.innodbLockMonitor = innodbLockMonitor;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
