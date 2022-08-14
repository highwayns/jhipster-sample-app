import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IHistoryActions, getHistoryActionsIdentifier } from '../history-actions.model';

export type EntityResponseType = HttpResponse<IHistoryActions>;
export type EntityArrayResponseType = HttpResponse<IHistoryActions[]>;

@Injectable({ providedIn: 'root' })
export class HistoryActionsService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/history-actions');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(historyActions: IHistoryActions): Observable<EntityResponseType> {
    return this.http.post<IHistoryActions>(this.resourceUrl, historyActions, { observe: 'response' });
  }

  update(historyActions: IHistoryActions): Observable<EntityResponseType> {
    return this.http.put<IHistoryActions>(`${this.resourceUrl}/${getHistoryActionsIdentifier(historyActions) as number}`, historyActions, {
      observe: 'response',
    });
  }

  partialUpdate(historyActions: IHistoryActions): Observable<EntityResponseType> {
    return this.http.patch<IHistoryActions>(
      `${this.resourceUrl}/${getHistoryActionsIdentifier(historyActions) as number}`,
      historyActions,
      { observe: 'response' }
    );
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IHistoryActions>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IHistoryActions[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addHistoryActionsToCollectionIfMissing(
    historyActionsCollection: IHistoryActions[],
    ...historyActionsToCheck: (IHistoryActions | null | undefined)[]
  ): IHistoryActions[] {
    const historyActions: IHistoryActions[] = historyActionsToCheck.filter(isPresent);
    if (historyActions.length > 0) {
      const historyActionsCollectionIdentifiers = historyActionsCollection.map(
        historyActionsItem => getHistoryActionsIdentifier(historyActionsItem)!
      );
      const historyActionsToAdd = historyActions.filter(historyActionsItem => {
        const historyActionsIdentifier = getHistoryActionsIdentifier(historyActionsItem);
        if (historyActionsIdentifier == null || historyActionsCollectionIdentifiers.includes(historyActionsIdentifier)) {
          return false;
        }
        historyActionsCollectionIdentifiers.push(historyActionsIdentifier);
        return true;
      });
      return [...historyActionsToAdd, ...historyActionsCollection];
    }
    return historyActionsCollection;
  }
}
