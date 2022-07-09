import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IPaymentJob, PaymentJob } from '../payment-job.model';
import { PaymentJobService } from '../service/payment-job.service';

@Injectable({ providedIn: 'root' })
export class PaymentJobRoutingResolveService implements Resolve<IPaymentJob> {
  constructor(protected service: PaymentJobService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IPaymentJob> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((paymentJob: HttpResponse<PaymentJob>) => {
          if (paymentJob.body) {
            return of(paymentJob.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new PaymentJob());
  }
}
