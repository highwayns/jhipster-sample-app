import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IAbuseReport, AbuseReport } from '../abuse-report.model';
import { AbuseReportService } from '../service/abuse-report.service';

@Injectable({ providedIn: 'root' })
export class AbuseReportRoutingResolveService implements Resolve<IAbuseReport> {
  constructor(protected service: AbuseReportService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IAbuseReport> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((abuseReport: HttpResponse<AbuseReport>) => {
          if (abuseReport.body) {
            return of(abuseReport.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new AbuseReport());
  }
}
