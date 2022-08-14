import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IIsoCountries, IsoCountries } from '../iso-countries.model';
import { IsoCountriesService } from '../service/iso-countries.service';

@Injectable({ providedIn: 'root' })
export class IsoCountriesRoutingResolveService implements Resolve<IIsoCountries> {
  constructor(protected service: IsoCountriesService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IIsoCountries> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((isoCountries: HttpResponse<IsoCountries>) => {
          if (isoCountries.body) {
            return of(isoCountries.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new IsoCountries());
  }
}
