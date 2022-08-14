import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IBitcoinAddresses, BitcoinAddresses } from '../bitcoin-addresses.model';
import { BitcoinAddressesService } from '../service/bitcoin-addresses.service';

@Injectable({ providedIn: 'root' })
export class BitcoinAddressesRoutingResolveService implements Resolve<IBitcoinAddresses> {
  constructor(protected service: BitcoinAddressesService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IBitcoinAddresses> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((bitcoinAddresses: HttpResponse<BitcoinAddresses>) => {
          if (bitcoinAddresses.body) {
            return of(bitcoinAddresses.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new BitcoinAddresses());
  }
}
