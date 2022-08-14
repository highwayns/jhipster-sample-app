import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IAdminControls, AdminControls } from '../admin-controls.model';
import { AdminControlsService } from '../service/admin-controls.service';

@Injectable({ providedIn: 'root' })
export class AdminControlsRoutingResolveService implements Resolve<IAdminControls> {
  constructor(protected service: AdminControlsService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IAdminControls> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((adminControls: HttpResponse<AdminControls>) => {
          if (adminControls.body) {
            return of(adminControls.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new AdminControls());
  }
}
