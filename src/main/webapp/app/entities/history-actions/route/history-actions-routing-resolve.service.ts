import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IHistoryActions, HistoryActions } from '../history-actions.model';
import { HistoryActionsService } from '../service/history-actions.service';

@Injectable({ providedIn: 'root' })
export class HistoryActionsRoutingResolveService implements Resolve<IHistoryActions> {
  constructor(protected service: HistoryActionsService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IHistoryActions> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((historyActions: HttpResponse<HistoryActions>) => {
          if (historyActions.body) {
            return of(historyActions.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new HistoryActions());
  }
}
