import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IPaymentAttributes, getPaymentAttributesIdentifier } from '../payment-attributes.model';

export type EntityResponseType = HttpResponse<IPaymentAttributes>;
export type EntityArrayResponseType = HttpResponse<IPaymentAttributes[]>;

@Injectable({ providedIn: 'root' })
export class PaymentAttributesService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/payment-attributes');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(paymentAttributes: IPaymentAttributes): Observable<EntityResponseType> {
    return this.http.post<IPaymentAttributes>(this.resourceUrl, paymentAttributes, { observe: 'response' });
  }

  update(paymentAttributes: IPaymentAttributes): Observable<EntityResponseType> {
    return this.http.put<IPaymentAttributes>(
      `${this.resourceUrl}/${getPaymentAttributesIdentifier(paymentAttributes) as number}`,
      paymentAttributes,
      { observe: 'response' }
    );
  }

  partialUpdate(paymentAttributes: IPaymentAttributes): Observable<EntityResponseType> {
    return this.http.patch<IPaymentAttributes>(
      `${this.resourceUrl}/${getPaymentAttributesIdentifier(paymentAttributes) as number}`,
      paymentAttributes,
      { observe: 'response' }
    );
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IPaymentAttributes>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IPaymentAttributes[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addPaymentAttributesToCollectionIfMissing(
    paymentAttributesCollection: IPaymentAttributes[],
    ...paymentAttributesToCheck: (IPaymentAttributes | null | undefined)[]
  ): IPaymentAttributes[] {
    const paymentAttributes: IPaymentAttributes[] = paymentAttributesToCheck.filter(isPresent);
    if (paymentAttributes.length > 0) {
      const paymentAttributesCollectionIdentifiers = paymentAttributesCollection.map(
        paymentAttributesItem => getPaymentAttributesIdentifier(paymentAttributesItem)!
      );
      const paymentAttributesToAdd = paymentAttributes.filter(paymentAttributesItem => {
        const paymentAttributesIdentifier = getPaymentAttributesIdentifier(paymentAttributesItem);
        if (paymentAttributesIdentifier == null || paymentAttributesCollectionIdentifiers.includes(paymentAttributesIdentifier)) {
          return false;
        }
        paymentAttributesCollectionIdentifiers.push(paymentAttributesIdentifier);
        return true;
      });
      return [...paymentAttributesToAdd, ...paymentAttributesCollection];
    }
    return paymentAttributesCollection;
  }
}
