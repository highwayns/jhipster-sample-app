import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { ICardTokenData, CardTokenData } from '../card-token-data.model';
import { CardTokenDataService } from '../service/card-token-data.service';

@Injectable({ providedIn: 'root' })
export class CardTokenDataRoutingResolveService implements Resolve<ICardTokenData> {
  constructor(protected service: CardTokenDataService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ICardTokenData> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((cardTokenData: HttpResponse<CardTokenData>) => {
          if (cardTokenData.body) {
            return of(cardTokenData.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new CardTokenData());
  }
}
