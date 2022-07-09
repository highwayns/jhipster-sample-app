import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IPaymentMethodInfo, getPaymentMethodInfoIdentifier } from '../payment-method-info.model';

export type EntityResponseType = HttpResponse<IPaymentMethodInfo>;
export type EntityArrayResponseType = HttpResponse<IPaymentMethodInfo[]>;

@Injectable({ providedIn: 'root' })
export class PaymentMethodInfoService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/payment-method-infos');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(paymentMethodInfo: IPaymentMethodInfo): Observable<EntityResponseType> {
    return this.http.post<IPaymentMethodInfo>(this.resourceUrl, paymentMethodInfo, { observe: 'response' });
  }

  update(paymentMethodInfo: IPaymentMethodInfo): Observable<EntityResponseType> {
    return this.http.put<IPaymentMethodInfo>(
      `${this.resourceUrl}/${getPaymentMethodInfoIdentifier(paymentMethodInfo) as number}`,
      paymentMethodInfo,
      { observe: 'response' }
    );
  }

  partialUpdate(paymentMethodInfo: IPaymentMethodInfo): Observable<EntityResponseType> {
    return this.http.patch<IPaymentMethodInfo>(
      `${this.resourceUrl}/${getPaymentMethodInfoIdentifier(paymentMethodInfo) as number}`,
      paymentMethodInfo,
      { observe: 'response' }
    );
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IPaymentMethodInfo>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IPaymentMethodInfo[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addPaymentMethodInfoToCollectionIfMissing(
    paymentMethodInfoCollection: IPaymentMethodInfo[],
    ...paymentMethodInfosToCheck: (IPaymentMethodInfo | null | undefined)[]
  ): IPaymentMethodInfo[] {
    const paymentMethodInfos: IPaymentMethodInfo[] = paymentMethodInfosToCheck.filter(isPresent);
    if (paymentMethodInfos.length > 0) {
      const paymentMethodInfoCollectionIdentifiers = paymentMethodInfoCollection.map(
        paymentMethodInfoItem => getPaymentMethodInfoIdentifier(paymentMethodInfoItem)!
      );
      const paymentMethodInfosToAdd = paymentMethodInfos.filter(paymentMethodInfoItem => {
        const paymentMethodInfoIdentifier = getPaymentMethodInfoIdentifier(paymentMethodInfoItem);
        if (paymentMethodInfoIdentifier == null || paymentMethodInfoCollectionIdentifiers.includes(paymentMethodInfoIdentifier)) {
          return false;
        }
        paymentMethodInfoCollectionIdentifiers.push(paymentMethodInfoIdentifier);
        return true;
      });
      return [...paymentMethodInfosToAdd, ...paymentMethodInfoCollection];
    }
    return paymentMethodInfoCollection;
  }
}
