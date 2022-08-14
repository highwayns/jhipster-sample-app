import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { ITransactionTypes, TransactionTypes } from '../transaction-types.model';
import { TransactionTypesService } from '../service/transaction-types.service';

@Injectable({ providedIn: 'root' })
export class TransactionTypesRoutingResolveService implements Resolve<ITransactionTypes> {
  constructor(protected service: TransactionTypesService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ITransactionTypes> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((transactionTypes: HttpResponse<TransactionTypes>) => {
          if (transactionTypes.body) {
            return of(transactionTypes.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new TransactionTypes());
  }
}
