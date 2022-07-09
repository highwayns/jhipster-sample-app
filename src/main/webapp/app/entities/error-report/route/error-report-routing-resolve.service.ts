import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IErrorReport, ErrorReport } from '../error-report.model';
import { ErrorReportService } from '../service/error-report.service';

@Injectable({ providedIn: 'root' })
export class ErrorReportRoutingResolveService implements Resolve<IErrorReport> {
  constructor(protected service: ErrorReportService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IErrorReport> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((errorReport: HttpResponse<ErrorReport>) => {
          if (errorReport.body) {
            return of(errorReport.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new ErrorReport());
  }
}
