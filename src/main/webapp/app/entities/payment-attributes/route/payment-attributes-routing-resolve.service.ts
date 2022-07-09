import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IPaymentAttributes, PaymentAttributes } from '../payment-attributes.model';
import { PaymentAttributesService } from '../service/payment-attributes.service';

@Injectable({ providedIn: 'root' })
export class PaymentAttributesRoutingResolveService implements Resolve<IPaymentAttributes> {
  constructor(protected service: PaymentAttributesService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IPaymentAttributes> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((paymentAttributes: HttpResponse<PaymentAttributes>) => {
          if (paymentAttributes.body) {
            return of(paymentAttributes.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new PaymentAttributes());
  }
}
