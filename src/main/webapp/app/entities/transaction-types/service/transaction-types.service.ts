import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { ITransactionTypes, getTransactionTypesIdentifier } from '../transaction-types.model';

export type EntityResponseType = HttpResponse<ITransactionTypes>;
export type EntityArrayResponseType = HttpResponse<ITransactionTypes[]>;

@Injectable({ providedIn: 'root' })
export class TransactionTypesService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/transaction-types');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(transactionTypes: ITransactionTypes): Observable<EntityResponseType> {
    return this.http.post<ITransactionTypes>(this.resourceUrl, transactionTypes, { observe: 'response' });
  }

  update(transactionTypes: ITransactionTypes): Observable<EntityResponseType> {
    return this.http.put<ITransactionTypes>(
      `${this.resourceUrl}/${getTransactionTypesIdentifier(transactionTypes) as number}`,
      transactionTypes,
      { observe: 'response' }
    );
  }

  partialUpdate(transactionTypes: ITransactionTypes): Observable<EntityResponseType> {
    return this.http.patch<ITransactionTypes>(
      `${this.resourceUrl}/${getTransactionTypesIdentifier(transactionTypes) as number}`,
      transactionTypes,
      { observe: 'response' }
    );
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ITransactionTypes>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ITransactionTypes[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addTransactionTypesToCollectionIfMissing(
    transactionTypesCollection: ITransactionTypes[],
    ...transactionTypesToCheck: (ITransactionTypes | null | undefined)[]
  ): ITransactionTypes[] {
    const transactionTypes: ITransactionTypes[] = transactionTypesToCheck.filter(isPresent);
    if (transactionTypes.length > 0) {
      const transactionTypesCollectionIdentifiers = transactionTypesCollection.map(
        transactionTypesItem => getTransactionTypesIdentifier(transactionTypesItem)!
      );
      const transactionTypesToAdd = transactionTypes.filter(transactionTypesItem => {
        const transactionTypesIdentifier = getTransactionTypesIdentifier(transactionTypesItem);
        if (transactionTypesIdentifier == null || transactionTypesCollectionIdentifiers.includes(transactionTypesIdentifier)) {
          return false;
        }
        transactionTypesCollectionIdentifiers.push(transactionTypesIdentifier);
        return true;
      });
      return [...transactionTypesToAdd, ...transactionTypesCollection];
    }
    return transactionTypesCollection;
  }
}
