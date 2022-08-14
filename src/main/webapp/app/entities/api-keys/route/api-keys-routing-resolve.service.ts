import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IApiKeys, ApiKeys } from '../api-keys.model';
import { ApiKeysService } from '../service/api-keys.service';

@Injectable({ providedIn: 'root' })
export class ApiKeysRoutingResolveService implements Resolve<IApiKeys> {
  constructor(protected service: ApiKeysService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IApiKeys> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((apiKeys: HttpResponse<ApiKeys>) => {
          if (apiKeys.body) {
            return of(apiKeys.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new ApiKeys());
  }
}
