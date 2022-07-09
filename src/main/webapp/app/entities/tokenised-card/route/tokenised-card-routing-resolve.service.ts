import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { ITokenisedCard, TokenisedCard } from '../tokenised-card.model';
import { TokenisedCardService } from '../service/tokenised-card.service';

@Injectable({ providedIn: 'root' })
export class TokenisedCardRoutingResolveService implements Resolve<ITokenisedCard> {
  constructor(protected service: TokenisedCardService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ITokenisedCard> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((tokenisedCard: HttpResponse<TokenisedCard>) => {
          if (tokenisedCard.body) {
            return of(tokenisedCard.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new TokenisedCard());
  }
}
