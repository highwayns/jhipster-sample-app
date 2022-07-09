import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IPaymentMethods, getPaymentMethodsIdentifier } from '../payment-methods.model';

export type EntityResponseType = HttpResponse<IPaymentMethods>;
export type EntityArrayResponseType = HttpResponse<IPaymentMethods[]>;

@Injectable({ providedIn: 'root' })
export class PaymentMethodsService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/payment-methods');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(paymentMethods: IPaymentMethods): Observable<EntityResponseType> {
    return this.http.post<IPaymentMethods>(this.resourceUrl, paymentMethods, { observe: 'response' });
  }

  update(paymentMethods: IPaymentMethods): Observable<EntityResponseType> {
    return this.http.put<IPaymentMethods>(`${this.resourceUrl}/${getPaymentMethodsIdentifier(paymentMethods) as number}`, paymentMethods, {
      observe: 'response',
    });
  }

  partialUpdate(paymentMethods: IPaymentMethods): Observable<EntityResponseType> {
    return this.http.patch<IPaymentMethods>(
      `${this.resourceUrl}/${getPaymentMethodsIdentifier(paymentMethods) as number}`,
      paymentMethods,
      { observe: 'response' }
    );
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IPaymentMethods>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IPaymentMethods[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addPaymentMethodsToCollectionIfMissing(
    paymentMethodsCollection: IPaymentMethods[],
    ...paymentMethodsToCheck: (IPaymentMethods | null | undefined)[]
  ): IPaymentMethods[] {
    const paymentMethods: IPaymentMethods[] = paymentMethodsToCheck.filter(isPresent);
    if (paymentMethods.length > 0) {
      const paymentMethodsCollectionIdentifiers = paymentMethodsCollection.map(
        paymentMethodsItem => getPaymentMethodsIdentifier(paymentMethodsItem)!
      );
      const paymentMethodsToAdd = paymentMethods.filter(paymentMethodsItem => {
        const paymentMethodsIdentifier = getPaymentMethodsIdentifier(paymentMethodsItem);
        if (paymentMethodsIdentifier == null || paymentMethodsCollectionIdentifiers.includes(paymentMethodsIdentifier)) {
          return false;
        }
        paymentMethodsCollectionIdentifiers.push(paymentMethodsIdentifier);
        return true;
      });
      return [...paymentMethodsToAdd, ...paymentMethodsCollection];
    }
    return paymentMethodsCollection;
  }
}
