import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { ICurrencys, getCurrencysIdentifier } from '../currencys.model';

export type EntityResponseType = HttpResponse<ICurrencys>;
export type EntityArrayResponseType = HttpResponse<ICurrencys[]>;

@Injectable({ providedIn: 'root' })
export class CurrencysService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/currencys');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(currencys: ICurrencys): Observable<EntityResponseType> {
    return this.http.post<ICurrencys>(this.resourceUrl, currencys, { observe: 'response' });
  }

  update(currencys: ICurrencys): Observable<EntityResponseType> {
    return this.http.put<ICurrencys>(`${this.resourceUrl}/${getCurrencysIdentifier(currencys) as number}`, currencys, {
      observe: 'response',
    });
  }

  partialUpdate(currencys: ICurrencys): Observable<EntityResponseType> {
    return this.http.patch<ICurrencys>(`${this.resourceUrl}/${getCurrencysIdentifier(currencys) as number}`, currencys, {
      observe: 'response',
    });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ICurrencys>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ICurrencys[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addCurrencysToCollectionIfMissing(
    currencysCollection: ICurrencys[],
    ...currencysToCheck: (ICurrencys | null | undefined)[]
  ): ICurrencys[] {
    const currencys: ICurrencys[] = currencysToCheck.filter(isPresent);
    if (currencys.length > 0) {
      const currencysCollectionIdentifiers = currencysCollection.map(currencysItem => getCurrencysIdentifier(currencysItem)!);
      const currencysToAdd = currencys.filter(currencysItem => {
        const currencysIdentifier = getCurrencysIdentifier(currencysItem);
        if (currencysIdentifier == null || currencysCollectionIdentifiers.includes(currencysIdentifier)) {
          return false;
        }
        currencysCollectionIdentifiers.push(currencysIdentifier);
        return true;
      });
      return [...currencysToAdd, ...currencysCollection];
    }
    return currencysCollection;
  }
}
