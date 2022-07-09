import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IRecurrenceCriteria, RecurrenceCriteria } from '../recurrence-criteria.model';
import { RecurrenceCriteriaService } from '../service/recurrence-criteria.service';

@Injectable({ providedIn: 'root' })
export class RecurrenceCriteriaRoutingResolveService implements Resolve<IRecurrenceCriteria> {
  constructor(protected service: RecurrenceCriteriaService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IRecurrenceCriteria> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((recurrenceCriteria: HttpResponse<RecurrenceCriteria>) => {
          if (recurrenceCriteria.body) {
            return of(recurrenceCriteria.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new RecurrenceCriteria());
  }
}
