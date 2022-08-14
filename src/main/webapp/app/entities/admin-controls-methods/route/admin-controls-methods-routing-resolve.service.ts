import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IAdminControlsMethods, AdminControlsMethods } from '../admin-controls-methods.model';
import { AdminControlsMethodsService } from '../service/admin-controls-methods.service';

@Injectable({ providedIn: 'root' })
export class AdminControlsMethodsRoutingResolveService implements Resolve<IAdminControlsMethods> {
  constructor(protected service: AdminControlsMethodsService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IAdminControlsMethods> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((adminControlsMethods: HttpResponse<AdminControlsMethods>) => {
          if (adminControlsMethods.body) {
            return of(adminControlsMethods.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new AdminControlsMethods());
  }
}
