import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IPaymentStep, PaymentStep } from '../payment-step.model';
import { PaymentStepService } from '../service/payment-step.service';

@Injectable({ providedIn: 'root' })
export class PaymentStepRoutingResolveService implements Resolve<IPaymentStep> {
  constructor(protected service: PaymentStepService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IPaymentStep> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((paymentStep: HttpResponse<PaymentStep>) => {
          if (paymentStep.body) {
            return of(paymentStep.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new PaymentStep());
  }
}
