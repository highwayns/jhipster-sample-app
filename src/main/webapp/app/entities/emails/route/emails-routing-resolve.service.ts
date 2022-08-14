import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IEmails, Emails } from '../emails.model';
import { EmailsService } from '../service/emails.service';

@Injectable({ providedIn: 'root' })
export class EmailsRoutingResolveService implements Resolve<IEmails> {
  constructor(protected service: EmailsService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IEmails> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((emails: HttpResponse<Emails>) => {
          if (emails.body) {
            return of(emails.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Emails());
  }
}
