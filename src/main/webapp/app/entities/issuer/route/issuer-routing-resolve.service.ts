import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IIssuer, Issuer } from '../issuer.model';
import { IssuerService } from '../service/issuer.service';

@Injectable({ providedIn: 'root' })
export class IssuerRoutingResolveService implements Resolve<IIssuer> {
  constructor(protected service: IssuerService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IIssuer> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((issuer: HttpResponse<Issuer>) => {
          if (issuer.body) {
            return of(issuer.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Issuer());
  }
}
