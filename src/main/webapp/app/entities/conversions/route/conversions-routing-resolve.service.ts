import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IConversions, Conversions } from '../conversions.model';
import { ConversionsService } from '../service/conversions.service';

@Injectable({ providedIn: 'root' })
export class ConversionsRoutingResolveService implements Resolve<IConversions> {
  constructor(protected service: ConversionsService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IConversions> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((conversions: HttpResponse<Conversions>) => {
          if (conversions.body) {
            return of(conversions.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Conversions());
  }
}
