import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IDailyReports, DailyReports } from '../daily-reports.model';
import { DailyReportsService } from '../service/daily-reports.service';

@Injectable({ providedIn: 'root' })
export class DailyReportsRoutingResolveService implements Resolve<IDailyReports> {
  constructor(protected service: DailyReportsService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IDailyReports> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((dailyReports: HttpResponse<DailyReports>) => {
          if (dailyReports.body) {
            return of(dailyReports.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new DailyReports());
  }
}
