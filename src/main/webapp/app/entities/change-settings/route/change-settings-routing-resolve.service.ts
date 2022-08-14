import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IChangeSettings, ChangeSettings } from '../change-settings.model';
import { ChangeSettingsService } from '../service/change-settings.service';

@Injectable({ providedIn: 'root' })
export class ChangeSettingsRoutingResolveService implements Resolve<IChangeSettings> {
  constructor(protected service: ChangeSettingsService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IChangeSettings> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((changeSettings: HttpResponse<ChangeSettings>) => {
          if (changeSettings.body) {
            return of(changeSettings.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new ChangeSettings());
  }
}
