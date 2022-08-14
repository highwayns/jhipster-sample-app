import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IOrderLog, OrderLog } from '../order-log.model';
import { OrderLogService } from '../service/order-log.service';

@Injectable({ providedIn: 'root' })
export class OrderLogRoutingResolveService implements Resolve<IOrderLog> {
  constructor(protected service: OrderLogService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IOrderLog> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((orderLog: HttpResponse<OrderLog>) => {
          if (orderLog.body) {
            return of(orderLog.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new OrderLog());
  }
}
