import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IIsoCountries, getIsoCountriesIdentifier } from '../iso-countries.model';

export type EntityResponseType = HttpResponse<IIsoCountries>;
export type EntityArrayResponseType = HttpResponse<IIsoCountries[]>;

@Injectable({ providedIn: 'root' })
export class IsoCountriesService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/iso-countries');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(isoCountries: IIsoCountries): Observable<EntityResponseType> {
    return this.http.post<IIsoCountries>(this.resourceUrl, isoCountries, { observe: 'response' });
  }

  update(isoCountries: IIsoCountries): Observable<EntityResponseType> {
    return this.http.put<IIsoCountries>(`${this.resourceUrl}/${getIsoCountriesIdentifier(isoCountries) as number}`, isoCountries, {
      observe: 'response',
    });
  }

  partialUpdate(isoCountries: IIsoCountries): Observable<EntityResponseType> {
    return this.http.patch<IIsoCountries>(`${this.resourceUrl}/${getIsoCountriesIdentifier(isoCountries) as number}`, isoCountries, {
      observe: 'response',
    });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IIsoCountries>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IIsoCountries[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addIsoCountriesToCollectionIfMissing(
    isoCountriesCollection: IIsoCountries[],
    ...isoCountriesToCheck: (IIsoCountries | null | undefined)[]
  ): IIsoCountries[] {
    const isoCountries: IIsoCountries[] = isoCountriesToCheck.filter(isPresent);
    if (isoCountries.length > 0) {
      const isoCountriesCollectionIdentifiers = isoCountriesCollection.map(
        isoCountriesItem => getIsoCountriesIdentifier(isoCountriesItem)!
      );
      const isoCountriesToAdd = isoCountries.filter(isoCountriesItem => {
        const isoCountriesIdentifier = getIsoCountriesIdentifier(isoCountriesItem);
        if (isoCountriesIdentifier == null || isoCountriesCollectionIdentifiers.includes(isoCountriesIdentifier)) {
          return false;
        }
        isoCountriesCollectionIdentifiers.push(isoCountriesIdentifier);
        return true;
      });
      return [...isoCountriesToAdd, ...isoCountriesCollection];
    }
    return isoCountriesCollection;
  }
}
