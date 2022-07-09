import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { ICapture, Capture } from '../capture.model';
import { CaptureService } from '../service/capture.service';

@Injectable({ providedIn: 'root' })
export class CaptureRoutingResolveService implements Resolve<ICapture> {
  constructor(protected service: CaptureService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ICapture> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((capture: HttpResponse<Capture>) => {
          if (capture.body) {
            return of(capture.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Capture());
  }
}
