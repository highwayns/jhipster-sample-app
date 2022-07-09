import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { ITokenisedCard, getTokenisedCardIdentifier } from '../tokenised-card.model';

export type EntityResponseType = HttpResponse<ITokenisedCard>;
export type EntityArrayResponseType = HttpResponse<ITokenisedCard[]>;

@Injectable({ providedIn: 'root' })
export class TokenisedCardService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/tokenised-cards');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(tokenisedCard: ITokenisedCard): Observable<EntityResponseType> {
    return this.http.post<ITokenisedCard>(this.resourceUrl, tokenisedCard, { observe: 'response' });
  }

  update(tokenisedCard: ITokenisedCard): Observable<EntityResponseType> {
    return this.http.put<ITokenisedCard>(`${this.resourceUrl}/${getTokenisedCardIdentifier(tokenisedCard) as number}`, tokenisedCard, {
      observe: 'response',
    });
  }

  partialUpdate(tokenisedCard: ITokenisedCard): Observable<EntityResponseType> {
    return this.http.patch<ITokenisedCard>(`${this.resourceUrl}/${getTokenisedCardIdentifier(tokenisedCard) as number}`, tokenisedCard, {
      observe: 'response',
    });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ITokenisedCard>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ITokenisedCard[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addTokenisedCardToCollectionIfMissing(
    tokenisedCardCollection: ITokenisedCard[],
    ...tokenisedCardsToCheck: (ITokenisedCard | null | undefined)[]
  ): ITokenisedCard[] {
    const tokenisedCards: ITokenisedCard[] = tokenisedCardsToCheck.filter(isPresent);
    if (tokenisedCards.length > 0) {
      const tokenisedCardCollectionIdentifiers = tokenisedCardCollection.map(
        tokenisedCardItem => getTokenisedCardIdentifier(tokenisedCardItem)!
      );
      const tokenisedCardsToAdd = tokenisedCards.filter(tokenisedCardItem => {
        const tokenisedCardIdentifier = getTokenisedCardIdentifier(tokenisedCardItem);
        if (tokenisedCardIdentifier == null || tokenisedCardCollectionIdentifiers.includes(tokenisedCardIdentifier)) {
          return false;
        }
        tokenisedCardCollectionIdentifiers.push(tokenisedCardIdentifier);
        return true;
      });
      return [...tokenisedCardsToAdd, ...tokenisedCardCollection];
    }
    return tokenisedCardCollection;
  }
}
