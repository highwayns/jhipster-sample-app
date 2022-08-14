import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IHistoricalData, HistoricalData } from '../historical-data.model';
import { HistoricalDataService } from '../service/historical-data.service';

@Injectable({ providedIn: 'root' })
export class HistoricalDataRoutingResolveService implements Resolve<IHistoricalData> {
  constructor(protected service: HistoricalDataService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IHistoricalData> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((historicalData: HttpResponse<HistoricalData>) => {
          if (historicalData.body) {
            return of(historicalData.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new HistoricalData());
  }
}
