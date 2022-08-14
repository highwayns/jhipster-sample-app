import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import dayjs from 'dayjs/esm';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IAdminSessions, getAdminSessionsIdentifier } from '../admin-sessions.model';

export type EntityResponseType = HttpResponse<IAdminSessions>;
export type EntityArrayResponseType = HttpResponse<IAdminSessions[]>;

@Injectable({ providedIn: 'root' })
export class AdminSessionsService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/admin-sessions');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(adminSessions: IAdminSessions): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(adminSessions);
    return this.http
      .post<IAdminSessions>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(adminSessions: IAdminSessions): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(adminSessions);
    return this.http
      .put<IAdminSessions>(`${this.resourceUrl}/${getAdminSessionsIdentifier(adminSessions) as number}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  partialUpdate(adminSessions: IAdminSessions): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(adminSessions);
    return this.http
      .patch<IAdminSessions>(`${this.resourceUrl}/${getAdminSessionsIdentifier(adminSessions) as number}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IAdminSessions>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IAdminSessions[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addAdminSessionsToCollectionIfMissing(
    adminSessionsCollection: IAdminSessions[],
    ...adminSessionsToCheck: (IAdminSessions | null | undefined)[]
  ): IAdminSessions[] {
    const adminSessions: IAdminSessions[] = adminSessionsToCheck.filter(isPresent);
    if (adminSessions.length > 0) {
      const adminSessionsCollectionIdentifiers = adminSessionsCollection.map(
        adminSessionsItem => getAdminSessionsIdentifier(adminSessionsItem)!
      );
      const adminSessionsToAdd = adminSessions.filter(adminSessionsItem => {
        const adminSessionsIdentifier = getAdminSessionsIdentifier(adminSessionsItem);
        if (adminSessionsIdentifier == null || adminSessionsCollectionIdentifiers.includes(adminSessionsIdentifier)) {
          return false;
        }
        adminSessionsCollectionIdentifiers.push(adminSessionsIdentifier);
        return true;
      });
      return [...adminSessionsToAdd, ...adminSessionsCollection];
    }
    return adminSessionsCollection;
  }

  protected convertDateFromClient(adminSessions: IAdminSessions): IAdminSessions {
    return Object.assign({}, adminSessions, {
      sessionTime: adminSessions.sessionTime?.isValid() ? adminSessions.sessionTime.toJSON() : undefined,
      sessionStart: adminSessions.sessionStart?.isValid() ? adminSessions.sessionStart.toJSON() : undefined,
    });
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.sessionTime = res.body.sessionTime ? dayjs(res.body.sessionTime) : undefined;
      res.body.sessionStart = res.body.sessionStart ? dayjs(res.body.sessionStart) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((adminSessions: IAdminSessions) => {
        adminSessions.sessionTime = adminSessions.sessionTime ? dayjs(adminSessions.sessionTime) : undefined;
        adminSessions.sessionStart = adminSessions.sessionStart ? dayjs(adminSessions.sessionStart) : undefined;
      });
    }
    return res;
  }
}
