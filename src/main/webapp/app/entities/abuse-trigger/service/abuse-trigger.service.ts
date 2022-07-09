import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IAbuseTrigger, getAbuseTriggerIdentifier } from '../abuse-trigger.model';

export type EntityResponseType = HttpResponse<IAbuseTrigger>;
export type EntityArrayResponseType = HttpResponse<IAbuseTrigger[]>;

@Injectable({ providedIn: 'root' })
export class AbuseTriggerService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/abuse-triggers');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(abuseTrigger: IAbuseTrigger): Observable<EntityResponseType> {
    return this.http.post<IAbuseTrigger>(this.resourceUrl, abuseTrigger, { observe: 'response' });
  }

  update(abuseTrigger: IAbuseTrigger): Observable<EntityResponseType> {
    return this.http.put<IAbuseTrigger>(`${this.resourceUrl}/${getAbuseTriggerIdentifier(abuseTrigger) as number}`, abuseTrigger, {
      observe: 'response',
    });
  }

  partialUpdate(abuseTrigger: IAbuseTrigger): Observable<EntityResponseType> {
    return this.http.patch<IAbuseTrigger>(`${this.resourceUrl}/${getAbuseTriggerIdentifier(abuseTrigger) as number}`, abuseTrigger, {
      observe: 'response',
    });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IAbuseTrigger>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IAbuseTrigger[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addAbuseTriggerToCollectionIfMissing(
    abuseTriggerCollection: IAbuseTrigger[],
    ...abuseTriggersToCheck: (IAbuseTrigger | null | undefined)[]
  ): IAbuseTrigger[] {
    const abuseTriggers: IAbuseTrigger[] = abuseTriggersToCheck.filter(isPresent);
    if (abuseTriggers.length > 0) {
      const abuseTriggerCollectionIdentifiers = abuseTriggerCollection.map(
        abuseTriggerItem => getAbuseTriggerIdentifier(abuseTriggerItem)!
      );
      const abuseTriggersToAdd = abuseTriggers.filter(abuseTriggerItem => {
        const abuseTriggerIdentifier = getAbuseTriggerIdentifier(abuseTriggerItem);
        if (abuseTriggerIdentifier == null || abuseTriggerCollectionIdentifiers.includes(abuseTriggerIdentifier)) {
          return false;
        }
        abuseTriggerCollectionIdentifiers.push(abuseTriggerIdentifier);
        return true;
      });
      return [...abuseTriggersToAdd, ...abuseTriggerCollection];
    }
    return abuseTriggerCollection;
  }
}
