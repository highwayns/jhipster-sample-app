import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IRequestDescriptions, RequestDescriptions } from '../request-descriptions.model';
import { RequestDescriptionsService } from '../service/request-descriptions.service';

@Injectable({ providedIn: 'root' })
export class RequestDescriptionsRoutingResolveService implements Resolve<IRequestDescriptions> {
  constructor(protected service: RequestDescriptionsService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IRequestDescriptions> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((requestDescriptions: HttpResponse<RequestDescriptions>) => {
          if (requestDescriptions.body) {
            return of(requestDescriptions.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new RequestDescriptions());
  }
}
