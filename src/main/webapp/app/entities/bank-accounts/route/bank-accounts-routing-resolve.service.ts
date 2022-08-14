import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IBankAccounts, BankAccounts } from '../bank-accounts.model';
import { BankAccountsService } from '../service/bank-accounts.service';

@Injectable({ providedIn: 'root' })
export class BankAccountsRoutingResolveService implements Resolve<IBankAccounts> {
  constructor(protected service: BankAccountsService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IBankAccounts> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((bankAccounts: HttpResponse<BankAccounts>) => {
          if (bankAccounts.body) {
            return of(bankAccounts.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new BankAccounts());
  }
}
