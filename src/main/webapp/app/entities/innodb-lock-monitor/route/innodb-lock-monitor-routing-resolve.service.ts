import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IInnodbLockMonitor, InnodbLockMonitor } from '../innodb-lock-monitor.model';
import { InnodbLockMonitorService } from '../service/innodb-lock-monitor.service';

@Injectable({ providedIn: 'root' })
export class InnodbLockMonitorRoutingResolveService implements Resolve<IInnodbLockMonitor> {
  constructor(protected service: InnodbLockMonitorService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IInnodbLockMonitor> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((innodbLockMonitor: HttpResponse<InnodbLockMonitor>) => {
          if (innodbLockMonitor.body) {
            return of(innodbLockMonitor.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new InnodbLockMonitor());
  }
}
