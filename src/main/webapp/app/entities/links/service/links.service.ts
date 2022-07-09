import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { ILinks, getLinksIdentifier } from '../links.model';

export type EntityResponseType = HttpResponse<ILinks>;
export type EntityArrayResponseType = HttpResponse<ILinks[]>;

@Injectable({ providedIn: 'root' })
export class LinksService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/links');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(links: ILinks): Observable<EntityResponseType> {
    return this.http.post<ILinks>(this.resourceUrl, links, { observe: 'response' });
  }

  update(links: ILinks): Observable<EntityResponseType> {
    return this.http.put<ILinks>(`${this.resourceUrl}/${getLinksIdentifier(links) as number}`, links, { observe: 'response' });
  }

  partialUpdate(links: ILinks): Observable<EntityResponseType> {
    return this.http.patch<ILinks>(`${this.resourceUrl}/${getLinksIdentifier(links) as number}`, links, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ILinks>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ILinks[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addLinksToCollectionIfMissing(linksCollection: ILinks[], ...linksToCheck: (ILinks | null | undefined)[]): ILinks[] {
    const links: ILinks[] = linksToCheck.filter(isPresent);
    if (links.length > 0) {
      const linksCollectionIdentifiers = linksCollection.map(linksItem => getLinksIdentifier(linksItem)!);
      const linksToAdd = links.filter(linksItem => {
        const linksIdentifier = getLinksIdentifier(linksItem);
        if (linksIdentifier == null || linksCollectionIdentifiers.includes(linksIdentifier)) {
          return false;
        }
        linksCollectionIdentifiers.push(linksIdentifier);
        return true;
      });
      return [...linksToAdd, ...linksCollection];
    }
    return linksCollection;
  }
}
