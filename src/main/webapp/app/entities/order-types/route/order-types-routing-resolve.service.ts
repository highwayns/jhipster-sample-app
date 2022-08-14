import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IOrderTypes, OrderTypes } from '../order-types.model';
import { OrderTypesService } from '../service/order-types.service';

@Injectable({ providedIn: 'root' })
export class OrderTypesRoutingResolveService implements Resolve<IOrderTypes> {
  constructor(protected service: OrderTypesService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IOrderTypes> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((orderTypes: HttpResponse<OrderTypes>) => {
          if (orderTypes.body) {
            return of(orderTypes.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new OrderTypes());
  }
}
