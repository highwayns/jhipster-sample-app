import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IAdminOrder, AdminOrder } from '../admin-order.model';
import { AdminOrderService } from '../service/admin-order.service';

@Injectable({ providedIn: 'root' })
export class AdminOrderRoutingResolveService implements Resolve<IAdminOrder> {
  constructor(protected service: AdminOrderService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IAdminOrder> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((adminOrder: HttpResponse<AdminOrder>) => {
          if (adminOrder.body) {
            return of(adminOrder.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new AdminOrder());
  }
}
