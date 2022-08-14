import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import dayjs from 'dayjs/esm';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { ISiteUsers, getSiteUsersIdentifier } from '../site-users.model';

export type EntityResponseType = HttpResponse<ISiteUsers>;
export type EntityArrayResponseType = HttpResponse<ISiteUsers[]>;

@Injectable({ providedIn: 'root' })
export class SiteUsersService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/site-users');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(siteUsers: ISiteUsers): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(siteUsers);
    return this.http
      .post<ISiteUsers>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(siteUsers: ISiteUsers): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(siteUsers);
    return this.http
      .put<ISiteUsers>(`${this.resourceUrl}/${getSiteUsersIdentifier(siteUsers) as number}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  partialUpdate(siteUsers: ISiteUsers): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(siteUsers);
    return this.http
      .patch<ISiteUsers>(`${this.resourceUrl}/${getSiteUsersIdentifier(siteUsers) as number}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<ISiteUsers>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<ISiteUsers[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addSiteUsersToCollectionIfMissing(
    siteUsersCollection: ISiteUsers[],
    ...siteUsersToCheck: (ISiteUsers | null | undefined)[]
  ): ISiteUsers[] {
    const siteUsers: ISiteUsers[] = siteUsersToCheck.filter(isPresent);
    if (siteUsers.length > 0) {
      const siteUsersCollectionIdentifiers = siteUsersCollection.map(siteUsersItem => getSiteUsersIdentifier(siteUsersItem)!);
      const siteUsersToAdd = siteUsers.filter(siteUsersItem => {
        const siteUsersIdentifier = getSiteUsersIdentifier(siteUsersItem);
        if (siteUsersIdentifier == null || siteUsersCollectionIdentifiers.includes(siteUsersIdentifier)) {
          return false;
        }
        siteUsersCollectionIdentifiers.push(siteUsersIdentifier);
        return true;
      });
      return [...siteUsersToAdd, ...siteUsersCollection];
    }
    return siteUsersCollection;
  }

  protected convertDateFromClient(siteUsers: ISiteUsers): ISiteUsers {
    return Object.assign({}, siteUsers, {
      date: siteUsers.date?.isValid() ? siteUsers.date.toJSON() : undefined,
      dontAskDate: siteUsers.dontAskDate?.isValid() ? siteUsers.dontAskDate.toJSON() : undefined,
      lastUpdate: siteUsers.lastUpdate?.isValid() ? siteUsers.lastUpdate.toJSON() : undefined,
    });
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.date = res.body.date ? dayjs(res.body.date) : undefined;
      res.body.dontAskDate = res.body.dontAskDate ? dayjs(res.body.dontAskDate) : undefined;
      res.body.lastUpdate = res.body.lastUpdate ? dayjs(res.body.lastUpdate) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((siteUsers: ISiteUsers) => {
        siteUsers.date = siteUsers.date ? dayjs(siteUsers.date) : undefined;
        siteUsers.dontAskDate = siteUsers.dontAskDate ? dayjs(siteUsers.dontAskDate) : undefined;
        siteUsers.lastUpdate = siteUsers.lastUpdate ? dayjs(siteUsers.lastUpdate) : undefined;
      });
    }
    return res;
  }
}
