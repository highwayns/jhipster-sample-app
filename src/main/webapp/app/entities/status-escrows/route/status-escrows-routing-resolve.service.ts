import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IStatusEscrows, StatusEscrows } from '../status-escrows.model';
import { StatusEscrowsService } from '../service/status-escrows.service';

@Injectable({ providedIn: 'root' })
export class StatusEscrowsRoutingResolveService implements Resolve<IStatusEscrows> {
  constructor(protected service: StatusEscrowsService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IStatusEscrows> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((statusEscrows: HttpResponse<StatusEscrows>) => {
          if (statusEscrows.body) {
            return of(statusEscrows.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new StatusEscrows());
  }
}
