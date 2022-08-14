import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IIpAccessLog, IpAccessLog } from '../ip-access-log.model';
import { IpAccessLogService } from '../service/ip-access-log.service';

@Injectable({ providedIn: 'root' })
export class IpAccessLogRoutingResolveService implements Resolve<IIpAccessLog> {
  constructor(protected service: IpAccessLogService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IIpAccessLog> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((ipAccessLog: HttpResponse<IpAccessLog>) => {
          if (ipAccessLog.body) {
            return of(ipAccessLog.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new IpAccessLog());
  }
}
