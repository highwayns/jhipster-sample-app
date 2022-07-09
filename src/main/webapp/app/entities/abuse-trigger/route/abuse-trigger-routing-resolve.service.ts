import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IAbuseTrigger, AbuseTrigger } from '../abuse-trigger.model';
import { AbuseTriggerService } from '../service/abuse-trigger.service';

@Injectable({ providedIn: 'root' })
export class AbuseTriggerRoutingResolveService implements Resolve<IAbuseTrigger> {
  constructor(protected service: AbuseTriggerService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IAbuseTrigger> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((abuseTrigger: HttpResponse<AbuseTrigger>) => {
          if (abuseTrigger.body) {
            return of(abuseTrigger.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new AbuseTrigger());
  }
}
