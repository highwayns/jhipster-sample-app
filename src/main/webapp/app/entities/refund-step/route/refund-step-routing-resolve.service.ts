import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IRefundStep, RefundStep } from '../refund-step.model';
import { RefundStepService } from '../service/refund-step.service';

@Injectable({ providedIn: 'root' })
export class RefundStepRoutingResolveService implements Resolve<IRefundStep> {
  constructor(protected service: RefundStepService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IRefundStep> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((refundStep: HttpResponse<RefundStep>) => {
          if (refundStep.body) {
            return of(refundStep.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new RefundStep());
  }
}
