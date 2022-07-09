import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IPaymentJobAttributes, getPaymentJobAttributesIdentifier } from '../payment-job-attributes.model';

export type EntityResponseType = HttpResponse<IPaymentJobAttributes>;
export type EntityArrayResponseType = HttpResponse<IPaymentJobAttributes[]>;

@Injectable({ providedIn: 'root' })
export class PaymentJobAttributesService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/payment-job-attributes');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(paymentJobAttributes: IPaymentJobAttributes): Observable<EntityResponseType> {
    return this.http.post<IPaymentJobAttributes>(this.resourceUrl, paymentJobAttributes, { observe: 'response' });
  }

  update(paymentJobAttributes: IPaymentJobAttributes): Observable<EntityResponseType> {
    return this.http.put<IPaymentJobAttributes>(
      `${this.resourceUrl}/${getPaymentJobAttributesIdentifier(paymentJobAttributes) as number}`,
      paymentJobAttributes,
      { observe: 'response' }
    );
  }

  partialUpdate(paymentJobAttributes: IPaymentJobAttributes): Observable<EntityResponseType> {
    return this.http.patch<IPaymentJobAttributes>(
      `${this.resourceUrl}/${getPaymentJobAttributesIdentifier(paymentJobAttributes) as number}`,
      paymentJobAttributes,
      { observe: 'response' }
    );
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IPaymentJobAttributes>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IPaymentJobAttributes[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addPaymentJobAttributesToCollectionIfMissing(
    paymentJobAttributesCollection: IPaymentJobAttributes[],
    ...paymentJobAttributesToCheck: (IPaymentJobAttributes | null | undefined)[]
  ): IPaymentJobAttributes[] {
    const paymentJobAttributes: IPaymentJobAttributes[] = paymentJobAttributesToCheck.filter(isPresent);
    if (paymentJobAttributes.length > 0) {
      const paymentJobAttributesCollectionIdentifiers = paymentJobAttributesCollection.map(
        paymentJobAttributesItem => getPaymentJobAttributesIdentifier(paymentJobAttributesItem)!
      );
      const paymentJobAttributesToAdd = paymentJobAttributes.filter(paymentJobAttributesItem => {
        const paymentJobAttributesIdentifier = getPaymentJobAttributesIdentifier(paymentJobAttributesItem);
        if (paymentJobAttributesIdentifier == null || paymentJobAttributesCollectionIdentifiers.includes(paymentJobAttributesIdentifier)) {
          return false;
        }
        paymentJobAttributesCollectionIdentifiers.push(paymentJobAttributesIdentifier);
        return true;
      });
      return [...paymentJobAttributesToAdd, ...paymentJobAttributesCollection];
    }
    return paymentJobAttributesCollection;
  }
}
