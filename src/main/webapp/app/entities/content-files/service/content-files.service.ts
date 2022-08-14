import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IContentFiles, getContentFilesIdentifier } from '../content-files.model';

export type EntityResponseType = HttpResponse<IContentFiles>;
export type EntityArrayResponseType = HttpResponse<IContentFiles[]>;

@Injectable({ providedIn: 'root' })
export class ContentFilesService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/content-files');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(contentFiles: IContentFiles): Observable<EntityResponseType> {
    return this.http.post<IContentFiles>(this.resourceUrl, contentFiles, { observe: 'response' });
  }

  update(contentFiles: IContentFiles): Observable<EntityResponseType> {
    return this.http.put<IContentFiles>(`${this.resourceUrl}/${getContentFilesIdentifier(contentFiles) as number}`, contentFiles, {
      observe: 'response',
    });
  }

  partialUpdate(contentFiles: IContentFiles): Observable<EntityResponseType> {
    return this.http.patch<IContentFiles>(`${this.resourceUrl}/${getContentFilesIdentifier(contentFiles) as number}`, contentFiles, {
      observe: 'response',
    });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IContentFiles>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IContentFiles[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addContentFilesToCollectionIfMissing(
    contentFilesCollection: IContentFiles[],
    ...contentFilesToCheck: (IContentFiles | null | undefined)[]
  ): IContentFiles[] {
    const contentFiles: IContentFiles[] = contentFilesToCheck.filter(isPresent);
    if (contentFiles.length > 0) {
      const contentFilesCollectionIdentifiers = contentFilesCollection.map(
        contentFilesItem => getContentFilesIdentifier(contentFilesItem)!
      );
      const contentFilesToAdd = contentFiles.filter(contentFilesItem => {
        const contentFilesIdentifier = getContentFilesIdentifier(contentFilesItem);
        if (contentFilesIdentifier == null || contentFilesCollectionIdentifiers.includes(contentFilesIdentifier)) {
          return false;
        }
        contentFilesCollectionIdentifiers.push(contentFilesIdentifier);
        return true;
      });
      return [...contentFilesToAdd, ...contentFilesCollection];
    }
    return contentFilesCollection;
  }
}
