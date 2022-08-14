import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { ISettingsFiles, getSettingsFilesIdentifier } from '../settings-files.model';

export type EntityResponseType = HttpResponse<ISettingsFiles>;
export type EntityArrayResponseType = HttpResponse<ISettingsFiles[]>;

@Injectable({ providedIn: 'root' })
export class SettingsFilesService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/settings-files');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(settingsFiles: ISettingsFiles): Observable<EntityResponseType> {
    return this.http.post<ISettingsFiles>(this.resourceUrl, settingsFiles, { observe: 'response' });
  }

  update(settingsFiles: ISettingsFiles): Observable<EntityResponseType> {
    return this.http.put<ISettingsFiles>(`${this.resourceUrl}/${getSettingsFilesIdentifier(settingsFiles) as number}`, settingsFiles, {
      observe: 'response',
    });
  }

  partialUpdate(settingsFiles: ISettingsFiles): Observable<EntityResponseType> {
    return this.http.patch<ISettingsFiles>(`${this.resourceUrl}/${getSettingsFilesIdentifier(settingsFiles) as number}`, settingsFiles, {
      observe: 'response',
    });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ISettingsFiles>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ISettingsFiles[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addSettingsFilesToCollectionIfMissing(
    settingsFilesCollection: ISettingsFiles[],
    ...settingsFilesToCheck: (ISettingsFiles | null | undefined)[]
  ): ISettingsFiles[] {
    const settingsFiles: ISettingsFiles[] = settingsFilesToCheck.filter(isPresent);
    if (settingsFiles.length > 0) {
      const settingsFilesCollectionIdentifiers = settingsFilesCollection.map(
        settingsFilesItem => getSettingsFilesIdentifier(settingsFilesItem)!
      );
      const settingsFilesToAdd = settingsFiles.filter(settingsFilesItem => {
        const settingsFilesIdentifier = getSettingsFilesIdentifier(settingsFilesItem);
        if (settingsFilesIdentifier == null || settingsFilesCollectionIdentifiers.includes(settingsFilesIdentifier)) {
          return false;
        }
        settingsFilesCollectionIdentifiers.push(settingsFilesIdentifier);
        return true;
      });
      return [...settingsFilesToAdd, ...settingsFilesCollection];
    }
    return settingsFilesCollection;
  }
}
