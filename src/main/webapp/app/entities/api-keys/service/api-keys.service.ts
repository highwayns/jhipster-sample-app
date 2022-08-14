import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IApiKeys, getApiKeysIdentifier } from '../api-keys.model';

export type EntityResponseType = HttpResponse<IApiKeys>;
export type EntityArrayResponseType = HttpResponse<IApiKeys[]>;

@Injectable({ providedIn: 'root' })
export class ApiKeysService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/api-keys');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(apiKeys: IApiKeys): Observable<EntityResponseType> {
    return this.http.post<IApiKeys>(this.resourceUrl, apiKeys, { observe: 'response' });
  }

  update(apiKeys: IApiKeys): Observable<EntityResponseType> {
    return this.http.put<IApiKeys>(`${this.resourceUrl}/${getApiKeysIdentifier(apiKeys) as number}`, apiKeys, { observe: 'response' });
  }

  partialUpdate(apiKeys: IApiKeys): Observable<EntityResponseType> {
    return this.http.patch<IApiKeys>(`${this.resourceUrl}/${getApiKeysIdentifier(apiKeys) as number}`, apiKeys, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IApiKeys>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IApiKeys[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addApiKeysToCollectionIfMissing(apiKeysCollection: IApiKeys[], ...apiKeysToCheck: (IApiKeys | null | undefined)[]): IApiKeys[] {
    const apiKeys: IApiKeys[] = apiKeysToCheck.filter(isPresent);
    if (apiKeys.length > 0) {
      const apiKeysCollectionIdentifiers = apiKeysCollection.map(apiKeysItem => getApiKeysIdentifier(apiKeysItem)!);
      const apiKeysToAdd = apiKeys.filter(apiKeysItem => {
        const apiKeysIdentifier = getApiKeysIdentifier(apiKeysItem);
        if (apiKeysIdentifier == null || apiKeysCollectionIdentifiers.includes(apiKeysIdentifier)) {
          return false;
        }
        apiKeysCollectionIdentifiers.push(apiKeysIdentifier);
        return true;
      });
      return [...apiKeysToAdd, ...apiKeysCollection];
    }
    return apiKeysCollection;
  }
}
