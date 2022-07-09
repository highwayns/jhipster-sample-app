import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { ICurrencys, Currencys } from '../currencys.model';
import { CurrencysService } from '../service/currencys.service';

@Injectable({ providedIn: 'root' })
export class CurrencysRoutingResolveService implements Resolve<ICurrencys> {
  constructor(protected service: CurrencysService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ICurrencys> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((currencys: HttpResponse<Currencys>) => {
          if (currencys.body) {
            return of(currencys.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Currencys());
  }
}
