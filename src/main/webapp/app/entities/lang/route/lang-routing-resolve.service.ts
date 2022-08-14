import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { ILang, Lang } from '../lang.model';
import { LangService } from '../service/lang.service';

@Injectable({ providedIn: 'root' })
export class LangRoutingResolveService implements Resolve<ILang> {
  constructor(protected service: LangService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ILang> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((lang: HttpResponse<Lang>) => {
          if (lang.body) {
            return of(lang.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Lang());
  }
}
