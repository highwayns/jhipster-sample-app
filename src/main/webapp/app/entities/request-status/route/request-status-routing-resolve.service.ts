import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IRequestStatus, RequestStatus } from '../request-status.model';
import { RequestStatusService } from '../service/request-status.service';

@Injectable({ providedIn: 'root' })
export class RequestStatusRoutingResolveService implements Resolve<IRequestStatus> {
  constructor(protected service: RequestStatusService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IRequestStatus> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((requestStatus: HttpResponse<RequestStatus>) => {
          if (requestStatus.body) {
            return of(requestStatus.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new RequestStatus());
  }
}
