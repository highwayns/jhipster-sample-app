import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IIpAccessLog } from '../ip-access-log.model';

@Component({
  selector: 'jhi-ip-access-log-detail',
  templateUrl: './ip-access-log-detail.component.html',
})
export class IpAccessLogDetailComponent implements OnInit {
  ipAccessLog: IIpAccessLog | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ ipAccessLog }) => {
      this.ipAccessLog = ipAccessLog;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
