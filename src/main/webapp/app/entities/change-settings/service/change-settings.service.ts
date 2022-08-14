import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import dayjs from 'dayjs/esm';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IChangeSettings, getChangeSettingsIdentifier } from '../change-settings.model';

export type EntityResponseType = HttpResponse<IChangeSettings>;
export type EntityArrayResponseType = HttpResponse<IChangeSettings[]>;

@Injectable({ providedIn: 'root' })
export class ChangeSettingsService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/change-settings');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(changeSettings: IChangeSettings): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(changeSettings);
    return this.http
      .post<IChangeSettings>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(changeSettings: IChangeSettings): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(changeSettings);
    return this.http
      .put<IChangeSettings>(`${this.resourceUrl}/${getChangeSettingsIdentifier(changeSettings) as number}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  partialUpdate(changeSettings: IChangeSettings): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(changeSettings);
    return this.http
      .patch<IChangeSettings>(`${this.resourceUrl}/${getChangeSettingsIdentifier(changeSettings) as number}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IChangeSettings>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IChangeSettings[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addChangeSettingsToCollectionIfMissing(
    changeSettingsCollection: IChangeSettings[],
    ...changeSettingsToCheck: (IChangeSettings | null | undefined)[]
  ): IChangeSettings[] {
    const changeSettings: IChangeSettings[] = changeSettingsToCheck.filter(isPresent);
    if (changeSettings.length > 0) {
      const changeSettingsCollectionIdentifiers = changeSettingsCollection.map(
        changeSettingsItem => getChangeSettingsIdentifier(changeSettingsItem)!
      );
      const changeSettingsToAdd = changeSettings.filter(changeSettingsItem => {
        const changeSettingsIdentifier = getChangeSettingsIdentifier(changeSettingsItem);
        if (changeSettingsIdentifier == null || changeSettingsCollectionIdentifiers.includes(changeSettingsIdentifier)) {
          return false;
        }
        changeSettingsCollectionIdentifiers.push(changeSettingsIdentifier);
        return true;
      });
      return [...changeSettingsToAdd, ...changeSettingsCollection];
    }
    return changeSettingsCollection;
  }

  protected convertDateFromClient(changeSettings: IChangeSettings): IChangeSettings {
    return Object.assign({}, changeSettings, {
      date: changeSettings.date?.isValid() ? changeSettings.date.toJSON() : undefined,
    });
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.date = res.body.date ? dayjs(res.body.date) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((changeSettings: IChangeSettings) => {
        changeSettings.date = changeSettings.date ? dayjs(changeSettings.date) : undefined;
      });
    }
    return res;
  }
}
