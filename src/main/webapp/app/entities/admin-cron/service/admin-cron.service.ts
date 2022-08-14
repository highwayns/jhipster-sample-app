import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IAdminCron, getAdminCronIdentifier } from '../admin-cron.model';

export type EntityResponseType = HttpResponse<IAdminCron>;
export type EntityArrayResponseType = HttpResponse<IAdminCron[]>;

@Injectable({ providedIn: 'root' })
export class AdminCronService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/admin-crons');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(adminCron: IAdminCron): Observable<EntityResponseType> {
    return this.http.post<IAdminCron>(this.resourceUrl, adminCron, { observe: 'response' });
  }

  update(adminCron: IAdminCron): Observable<EntityResponseType> {
    return this.http.put<IAdminCron>(`${this.resourceUrl}/${getAdminCronIdentifier(adminCron) as number}`, adminCron, {
      observe: 'response',
    });
  }

  partialUpdate(adminCron: IAdminCron): Observable<EntityResponseType> {
    return this.http.patch<IAdminCron>(`${this.resourceUrl}/${getAdminCronIdentifier(adminCron) as number}`, adminCron, {
      observe: 'response',
    });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IAdminCron>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IAdminCron[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addAdminCronToCollectionIfMissing(
    adminCronCollection: IAdminCron[],
    ...adminCronsToCheck: (IAdminCron | null | undefined)[]
  ): IAdminCron[] {
    const adminCrons: IAdminCron[] = adminCronsToCheck.filter(isPresent);
    if (adminCrons.length > 0) {
      const adminCronCollectionIdentifiers = adminCronCollection.map(adminCronItem => getAdminCronIdentifier(adminCronItem)!);
      const adminCronsToAdd = adminCrons.filter(adminCronItem => {
        const adminCronIdentifier = getAdminCronIdentifier(adminCronItem);
        if (adminCronIdentifier == null || adminCronCollectionIdentifiers.includes(adminCronIdentifier)) {
          return false;
        }
        adminCronCollectionIdentifiers.push(adminCronIdentifier);
        return true;
      });
      return [...adminCronsToAdd, ...adminCronCollection];
    }
    return adminCronCollection;
  }
}
