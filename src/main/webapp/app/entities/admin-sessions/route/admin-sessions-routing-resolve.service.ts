import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IAdminSessions, AdminSessions } from '../admin-sessions.model';
import { AdminSessionsService } from '../service/admin-sessions.service';

@Injectable({ providedIn: 'root' })
export class AdminSessionsRoutingResolveService implements Resolve<IAdminSessions> {
  constructor(protected service: AdminSessionsService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IAdminSessions> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((adminSessions: HttpResponse<AdminSessions>) => {
          if (adminSessions.body) {
            return of(adminSessions.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new AdminSessions());
  }
}
