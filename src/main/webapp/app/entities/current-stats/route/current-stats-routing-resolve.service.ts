import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { ICurrentStats, CurrentStats } from '../current-stats.model';
import { CurrentStatsService } from '../service/current-stats.service';

@Injectable({ providedIn: 'root' })
export class CurrentStatsRoutingResolveService implements Resolve<ICurrentStats> {
  constructor(protected service: CurrentStatsService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ICurrentStats> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((currentStats: HttpResponse<CurrentStats>) => {
          if (currentStats.body) {
            return of(currentStats.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new CurrentStats());
  }
}
