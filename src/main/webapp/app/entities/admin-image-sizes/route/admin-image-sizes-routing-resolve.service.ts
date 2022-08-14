import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IAdminImageSizes, AdminImageSizes } from '../admin-image-sizes.model';
import { AdminImageSizesService } from '../service/admin-image-sizes.service';

@Injectable({ providedIn: 'root' })
export class AdminImageSizesRoutingResolveService implements Resolve<IAdminImageSizes> {
  constructor(protected service: AdminImageSizesService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IAdminImageSizes> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((adminImageSizes: HttpResponse<AdminImageSizes>) => {
          if (adminImageSizes.body) {
            return of(adminImageSizes.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new AdminImageSizes());
  }
}
