import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IBitcoindLog, BitcoindLog } from '../bitcoind-log.model';
import { BitcoindLogService } from '../service/bitcoind-log.service';

@Injectable({ providedIn: 'root' })
export class BitcoindLogRoutingResolveService implements Resolve<IBitcoindLog> {
  constructor(protected service: BitcoindLogService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IBitcoindLog> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((bitcoindLog: HttpResponse<BitcoindLog>) => {
          if (bitcoindLog.body) {
            return of(bitcoindLog.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new BitcoindLog());
  }
}
