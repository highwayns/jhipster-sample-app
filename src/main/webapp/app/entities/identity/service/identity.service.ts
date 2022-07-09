import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import dayjs from 'dayjs/esm';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IIdentity, getIdentityIdentifier } from '../identity.model';

export type EntityResponseType = HttpResponse<IIdentity>;
export type EntityArrayResponseType = HttpResponse<IIdentity[]>;

@Injectable({ providedIn: 'root' })
export class IdentityService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/identities');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(identity: IIdentity): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(identity);
    return this.http
      .post<IIdentity>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(identity: IIdentity): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(identity);
    return this.http
      .put<IIdentity>(`${this.resourceUrl}/${getIdentityIdentifier(identity) as number}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  partialUpdate(identity: IIdentity): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(identity);
    return this.http
      .patch<IIdentity>(`${this.resourceUrl}/${getIdentityIdentifier(identity) as number}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IIdentity>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IIdentity[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addIdentityToCollectionIfMissing(identityCollection: IIdentity[], ...identitiesToCheck: (IIdentity | null | undefined)[]): IIdentity[] {
    const identities: IIdentity[] = identitiesToCheck.filter(isPresent);
    if (identities.length > 0) {
      const identityCollectionIdentifiers = identityCollection.map(identityItem => getIdentityIdentifier(identityItem)!);
      const identitiesToAdd = identities.filter(identityItem => {
        const identityIdentifier = getIdentityIdentifier(identityItem);
        if (identityIdentifier == null || identityCollectionIdentifiers.includes(identityIdentifier)) {
          return false;
        }
        identityCollectionIdentifiers.push(identityIdentifier);
        return true;
      });
      return [...identitiesToAdd, ...identityCollection];
    }
    return identityCollection;
  }

  protected convertDateFromClient(identity: IIdentity): IIdentity {
    return Object.assign({}, identity, {
      dateOfBirth: identity.dateOfBirth?.isValid() ? identity.dateOfBirth.toJSON() : undefined,
    });
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.dateOfBirth = res.body.dateOfBirth ? dayjs(res.body.dateOfBirth) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((identity: IIdentity) => {
        identity.dateOfBirth = identity.dateOfBirth ? dayjs(identity.dateOfBirth) : undefined;
      });
    }
    return res;
  }
}
