import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { ILang, getLangIdentifier } from '../lang.model';

export type EntityResponseType = HttpResponse<ILang>;
export type EntityArrayResponseType = HttpResponse<ILang[]>;

@Injectable({ providedIn: 'root' })
export class LangService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/langs');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(lang: ILang): Observable<EntityResponseType> {
    return this.http.post<ILang>(this.resourceUrl, lang, { observe: 'response' });
  }

  update(lang: ILang): Observable<EntityResponseType> {
    return this.http.put<ILang>(`${this.resourceUrl}/${getLangIdentifier(lang) as number}`, lang, { observe: 'response' });
  }

  partialUpdate(lang: ILang): Observable<EntityResponseType> {
    return this.http.patch<ILang>(`${this.resourceUrl}/${getLangIdentifier(lang) as number}`, lang, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ILang>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ILang[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addLangToCollectionIfMissing(langCollection: ILang[], ...langsToCheck: (ILang | null | undefined)[]): ILang[] {
    const langs: ILang[] = langsToCheck.filter(isPresent);
    if (langs.length > 0) {
      const langCollectionIdentifiers = langCollection.map(langItem => getLangIdentifier(langItem)!);
      const langsToAdd = langs.filter(langItem => {
        const langIdentifier = getLangIdentifier(langItem);
        if (langIdentifier == null || langCollectionIdentifiers.includes(langIdentifier)) {
          return false;
        }
        langCollectionIdentifiers.push(langIdentifier);
        return true;
      });
      return [...langsToAdd, ...langCollection];
    }
    return langCollection;
  }
}
