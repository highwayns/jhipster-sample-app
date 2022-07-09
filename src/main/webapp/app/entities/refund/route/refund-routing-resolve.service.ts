import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IRefund, Refund } from '../refund.model';
import { RefundService } from '../service/refund.service';

@Injectable({ providedIn: 'root' })
export class RefundRoutingResolveService implements Resolve<IRefund> {
  constructor(protected service: RefundService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IRefund> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((refund: HttpResponse<Refund>) => {
          if (refund.body) {
            return of(refund.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Refund());
  }
}
