import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IPaymentMethodInfo, PaymentMethodInfo } from '../payment-method-info.model';
import { PaymentMethodInfoService } from '../service/payment-method-info.service';

@Injectable({ providedIn: 'root' })
export class PaymentMethodInfoRoutingResolveService implements Resolve<IPaymentMethodInfo> {
  constructor(protected service: PaymentMethodInfoService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IPaymentMethodInfo> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((paymentMethodInfo: HttpResponse<PaymentMethodInfo>) => {
          if (paymentMethodInfo.body) {
            return of(paymentMethodInfo.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new PaymentMethodInfo());
  }
}
