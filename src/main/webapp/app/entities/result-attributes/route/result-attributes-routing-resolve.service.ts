import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IResultAttributes, ResultAttributes } from '../result-attributes.model';
import { ResultAttributesService } from '../service/result-attributes.service';

@Injectable({ providedIn: 'root' })
export class ResultAttributesRoutingResolveService implements Resolve<IResultAttributes> {
  constructor(protected service: ResultAttributesService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IResultAttributes> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((resultAttributes: HttpResponse<ResultAttributes>) => {
          if (resultAttributes.body) {
            return of(resultAttributes.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new ResultAttributes());
  }
}
