import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IRequestTypes, RequestTypes } from '../request-types.model';
import { RequestTypesService } from '../service/request-types.service';

@Injectable({ providedIn: 'root' })
export class RequestTypesRoutingResolveService implements Resolve<IRequestTypes> {
  constructor(protected service: RequestTypesService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IRequestTypes> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((requestTypes: HttpResponse<RequestTypes>) => {
          if (requestTypes.body) {
            return of(requestTypes.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new RequestTypes());
  }
}
