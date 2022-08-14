import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IMonthlyReports, MonthlyReports } from '../monthly-reports.model';
import { MonthlyReportsService } from '../service/monthly-reports.service';

@Injectable({ providedIn: 'root' })
export class MonthlyReportsRoutingResolveService implements Resolve<IMonthlyReports> {
  constructor(protected service: MonthlyReportsService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IMonthlyReports> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((monthlyReports: HttpResponse<MonthlyReports>) => {
          if (monthlyReports.body) {
            return of(monthlyReports.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new MonthlyReports());
  }
}
