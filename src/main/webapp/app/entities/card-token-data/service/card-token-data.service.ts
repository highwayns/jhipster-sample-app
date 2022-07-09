import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { ICardTokenData, getCardTokenDataIdentifier } from '../card-token-data.model';

export type EntityResponseType = HttpResponse<ICardTokenData>;
export type EntityArrayResponseType = HttpResponse<ICardTokenData[]>;

@Injectable({ providedIn: 'root' })
export class CardTokenDataService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/card-token-data');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(cardTokenData: ICardTokenData): Observable<EntityResponseType> {
    return this.http.post<ICardTokenData>(this.resourceUrl, cardTokenData, { observe: 'response' });
  }

  update(cardTokenData: ICardTokenData): Observable<EntityResponseType> {
    return this.http.put<ICardTokenData>(`${this.resourceUrl}/${getCardTokenDataIdentifier(cardTokenData) as number}`, cardTokenData, {
      observe: 'response',
    });
  }

  partialUpdate(cardTokenData: ICardTokenData): Observable<EntityResponseType> {
    return this.http.patch<ICardTokenData>(`${this.resourceUrl}/${getCardTokenDataIdentifier(cardTokenData) as number}`, cardTokenData, {
      observe: 'response',
    });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ICardTokenData>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ICardTokenData[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addCardTokenDataToCollectionIfMissing(
    cardTokenDataCollection: ICardTokenData[],
    ...cardTokenDataToCheck: (ICardTokenData | null | undefined)[]
  ): ICardTokenData[] {
    const cardTokenData: ICardTokenData[] = cardTokenDataToCheck.filter(isPresent);
    if (cardTokenData.length > 0) {
      const cardTokenDataCollectionIdentifiers = cardTokenDataCollection.map(
        cardTokenDataItem => getCardTokenDataIdentifier(cardTokenDataItem)!
      );
      const cardTokenDataToAdd = cardTokenData.filter(cardTokenDataItem => {
        const cardTokenDataIdentifier = getCardTokenDataIdentifier(cardTokenDataItem);
        if (cardTokenDataIdentifier == null || cardTokenDataCollectionIdentifiers.includes(cardTokenDataIdentifier)) {
          return false;
        }
        cardTokenDataCollectionIdentifiers.push(cardTokenDataIdentifier);
        return true;
      });
      return [...cardTokenDataToAdd, ...cardTokenDataCollection];
    }
    return cardTokenDataCollection;
  }
}
