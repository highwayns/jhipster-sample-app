import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IEntry, getEntryIdentifier } from '../entry.model';

export type EntityResponseType = HttpResponse<IEntry>;
export type EntityArrayResponseType = HttpResponse<IEntry[]>;

@Injectable({ providedIn: 'root' })
export class EntryService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/entries');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(entry: IEntry): Observable<EntityResponseType> {
    return this.http.post<IEntry>(this.resourceUrl, entry, { observe: 'response' });
  }

  update(entry: IEntry): Observable<EntityResponseType> {
    return this.http.put<IEntry>(`${this.resourceUrl}/${getEntryIdentifier(entry) as number}`, entry, { observe: 'response' });
  }

  partialUpdate(entry: IEntry): Observable<EntityResponseType> {
    return this.http.patch<IEntry>(`${this.resourceUrl}/${getEntryIdentifier(entry) as number}`, entry, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IEntry>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IEntry[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addEntryToCollectionIfMissing(entryCollection: IEntry[], ...entriesToCheck: (IEntry | null | undefined)[]): IEntry[] {
    const entries: IEntry[] = entriesToCheck.filter(isPresent);
    if (entries.length > 0) {
      const entryCollectionIdentifiers = entryCollection.map(entryItem => getEntryIdentifier(entryItem)!);
      const entriesToAdd = entries.filter(entryItem => {
        const entryIdentifier = getEntryIdentifier(entryItem);
        if (entryIdentifier == null || entryCollectionIdentifiers.includes(entryIdentifier)) {
          return false;
        }
        entryCollectionIdentifiers.push(entryIdentifier);
        return true;
      });
      return [...entriesToAdd, ...entryCollection];
    }
    return entryCollection;
  }
}
