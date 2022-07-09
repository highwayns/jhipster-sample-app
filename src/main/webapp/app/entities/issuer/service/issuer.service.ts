import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IIssuer, getIssuerIdentifier } from '../issuer.model';

export type EntityResponseType = HttpResponse<IIssuer>;
export type EntityArrayResponseType = HttpResponse<IIssuer[]>;

@Injectable({ providedIn: 'root' })
export class IssuerService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/issuers');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(issuer: IIssuer): Observable<EntityResponseType> {
    return this.http.post<IIssuer>(this.resourceUrl, issuer, { observe: 'response' });
  }

  update(issuer: IIssuer): Observable<EntityResponseType> {
    return this.http.put<IIssuer>(`${this.resourceUrl}/${getIssuerIdentifier(issuer) as string}`, issuer, { observe: 'response' });
  }

  partialUpdate(issuer: IIssuer): Observable<EntityResponseType> {
    return this.http.patch<IIssuer>(`${this.resourceUrl}/${getIssuerIdentifier(issuer) as string}`, issuer, { observe: 'response' });
  }

  find(id: string): Observable<EntityResponseType> {
    return this.http.get<IIssuer>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IIssuer[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: string): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addIssuerToCollectionIfMissing(issuerCollection: IIssuer[], ...issuersToCheck: (IIssuer | null | undefined)[]): IIssuer[] {
    const issuers: IIssuer[] = issuersToCheck.filter(isPresent);
    if (issuers.length > 0) {
      const issuerCollectionIdentifiers = issuerCollection.map(issuerItem => getIssuerIdentifier(issuerItem)!);
      const issuersToAdd = issuers.filter(issuerItem => {
        const issuerIdentifier = getIssuerIdentifier(issuerItem);
        if (issuerIdentifier == null || issuerCollectionIdentifiers.includes(issuerIdentifier)) {
          return false;
        }
        issuerCollectionIdentifiers.push(issuerIdentifier);
        return true;
      });
      return [...issuersToAdd, ...issuerCollection];
    }
    return issuerCollection;
  }
}
