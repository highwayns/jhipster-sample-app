import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IPaymentJobAttributes, PaymentJobAttributes } from '../payment-job-attributes.model';
import { PaymentJobAttributesService } from '../service/payment-job-attributes.service';

@Injectable({ providedIn: 'root' })
export class PaymentJobAttributesRoutingResolveService implements Resolve<IPaymentJobAttributes> {
  constructor(protected service: PaymentJobAttributesService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IPaymentJobAttributes> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((paymentJobAttributes: HttpResponse<PaymentJobAttributes>) => {
          if (paymentJobAttributes.body) {
            return of(paymentJobAttributes.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new PaymentJobAttributes());
  }
}
