import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IFeeSchedule, FeeSchedule } from '../fee-schedule.model';
import { FeeScheduleService } from '../service/fee-schedule.service';

@Injectable({ providedIn: 'root' })
export class FeeScheduleRoutingResolveService implements Resolve<IFeeSchedule> {
  constructor(protected service: FeeScheduleService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IFeeSchedule> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((feeSchedule: HttpResponse<FeeSchedule>) => {
          if (feeSchedule.body) {
            return of(feeSchedule.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new FeeSchedule());
  }
}
