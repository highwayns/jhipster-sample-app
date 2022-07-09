import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IResultAttributes, getResultAttributesIdentifier } from '../result-attributes.model';

export type EntityResponseType = HttpResponse<IResultAttributes>;
export type EntityArrayResponseType = HttpResponse<IResultAttributes[]>;

@Injectable({ providedIn: 'root' })
export class ResultAttributesService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/result-attributes');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(resultAttributes: IResultAttributes): Observable<EntityResponseType> {
    return this.http.post<IResultAttributes>(this.resourceUrl, resultAttributes, { observe: 'response' });
  }

  update(resultAttributes: IResultAttributes): Observable<EntityResponseType> {
    return this.http.put<IResultAttributes>(
      `${this.resourceUrl}/${getResultAttributesIdentifier(resultAttributes) as number}`,
      resultAttributes,
      { observe: 'response' }
    );
  }

  partialUpdate(resultAttributes: IResultAttributes): Observable<EntityResponseType> {
    return this.http.patch<IResultAttributes>(
      `${this.resourceUrl}/${getResultAttributesIdentifier(resultAttributes) as number}`,
      resultAttributes,
      { observe: 'response' }
    );
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IResultAttributes>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IResultAttributes[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addResultAttributesToCollectionIfMissing(
    resultAttributesCollection: IResultAttributes[],
    ...resultAttributesToCheck: (IResultAttributes | null | undefined)[]
  ): IResultAttributes[] {
    const resultAttributes: IResultAttributes[] = resultAttributesToCheck.filter(isPresent);
    if (resultAttributes.length > 0) {
      const resultAttributesCollectionIdentifiers = resultAttributesCollection.map(
        resultAttributesItem => getResultAttributesIdentifier(resultAttributesItem)!
      );
      const resultAttributesToAdd = resultAttributes.filter(resultAttributesItem => {
        const resultAttributesIdentifier = getResultAttributesIdentifier(resultAttributesItem);
        if (resultAttributesIdentifier == null || resultAttributesCollectionIdentifiers.includes(resultAttributesIdentifier)) {
          return false;
        }
        resultAttributesCollectionIdentifiers.push(resultAttributesIdentifier);
        return true;
      });
      return [...resultAttributesToAdd, ...resultAttributesCollection];
    }
    return resultAttributesCollection;
  }
}
