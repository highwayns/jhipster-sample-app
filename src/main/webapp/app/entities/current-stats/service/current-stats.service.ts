import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { ICurrentStats, getCurrentStatsIdentifier } from '../current-stats.model';

export type EntityResponseType = HttpResponse<ICurrentStats>;
export type EntityArrayResponseType = HttpResponse<ICurrentStats[]>;

@Injectable({ providedIn: 'root' })
export class CurrentStatsService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/current-stats');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(currentStats: ICurrentStats): Observable<EntityResponseType> {
    return this.http.post<ICurrentStats>(this.resourceUrl, currentStats, { observe: 'response' });
  }

  update(currentStats: ICurrentStats): Observable<EntityResponseType> {
    return this.http.put<ICurrentStats>(`${this.resourceUrl}/${getCurrentStatsIdentifier(currentStats) as number}`, currentStats, {
      observe: 'response',
    });
  }

  partialUpdate(currentStats: ICurrentStats): Observable<EntityResponseType> {
    return this.http.patch<ICurrentStats>(`${this.resourceUrl}/${getCurrentStatsIdentifier(currentStats) as number}`, currentStats, {
      observe: 'response',
    });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ICurrentStats>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ICurrentStats[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addCurrentStatsToCollectionIfMissing(
    currentStatsCollection: ICurrentStats[],
    ...currentStatsToCheck: (ICurrentStats | null | undefined)[]
  ): ICurrentStats[] {
    const currentStats: ICurrentStats[] = currentStatsToCheck.filter(isPresent);
    if (currentStats.length > 0) {
      const currentStatsCollectionIdentifiers = currentStatsCollection.map(
        currentStatsItem => getCurrentStatsIdentifier(currentStatsItem)!
      );
      const currentStatsToAdd = currentStats.filter(currentStatsItem => {
        const currentStatsIdentifier = getCurrentStatsIdentifier(currentStatsItem);
        if (currentStatsIdentifier == null || currentStatsCollectionIdentifiers.includes(currentStatsIdentifier)) {
          return false;
        }
        currentStatsCollectionIdentifiers.push(currentStatsIdentifier);
        return true;
      });
      return [...currentStatsToAdd, ...currentStatsCollection];
    }
    return currentStatsCollection;
  }
}
