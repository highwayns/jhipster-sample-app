import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import dayjs from 'dayjs/esm';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IBitcoinAddresses, getBitcoinAddressesIdentifier } from '../bitcoin-addresses.model';

export type EntityResponseType = HttpResponse<IBitcoinAddresses>;
export type EntityArrayResponseType = HttpResponse<IBitcoinAddresses[]>;

@Injectable({ providedIn: 'root' })
export class BitcoinAddressesService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/bitcoin-addresses');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(bitcoinAddresses: IBitcoinAddresses): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(bitcoinAddresses);
    return this.http
      .post<IBitcoinAddresses>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(bitcoinAddresses: IBitcoinAddresses): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(bitcoinAddresses);
    return this.http
      .put<IBitcoinAddresses>(`${this.resourceUrl}/${getBitcoinAddressesIdentifier(bitcoinAddresses) as number}`, copy, {
        observe: 'response',
      })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  partialUpdate(bitcoinAddresses: IBitcoinAddresses): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(bitcoinAddresses);
    return this.http
      .patch<IBitcoinAddresses>(`${this.resourceUrl}/${getBitcoinAddressesIdentifier(bitcoinAddresses) as number}`, copy, {
        observe: 'response',
      })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IBitcoinAddresses>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IBitcoinAddresses[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addBitcoinAddressesToCollectionIfMissing(
    bitcoinAddressesCollection: IBitcoinAddresses[],
    ...bitcoinAddressesToCheck: (IBitcoinAddresses | null | undefined)[]
  ): IBitcoinAddresses[] {
    const bitcoinAddresses: IBitcoinAddresses[] = bitcoinAddressesToCheck.filter(isPresent);
    if (bitcoinAddresses.length > 0) {
      const bitcoinAddressesCollectionIdentifiers = bitcoinAddressesCollection.map(
        bitcoinAddressesItem => getBitcoinAddressesIdentifier(bitcoinAddressesItem)!
      );
      const bitcoinAddressesToAdd = bitcoinAddresses.filter(bitcoinAddressesItem => {
        const bitcoinAddressesIdentifier = getBitcoinAddressesIdentifier(bitcoinAddressesItem);
        if (bitcoinAddressesIdentifier == null || bitcoinAddressesCollectionIdentifiers.includes(bitcoinAddressesIdentifier)) {
          return false;
        }
        bitcoinAddressesCollectionIdentifiers.push(bitcoinAddressesIdentifier);
        return true;
      });
      return [...bitcoinAddressesToAdd, ...bitcoinAddressesCollection];
    }
    return bitcoinAddressesCollection;
  }

  protected convertDateFromClient(bitcoinAddresses: IBitcoinAddresses): IBitcoinAddresses {
    return Object.assign({}, bitcoinAddresses, {
      date: bitcoinAddresses.date?.isValid() ? bitcoinAddresses.date.toJSON() : undefined,
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
      res.body.forEach((bitcoinAddresses: IBitcoinAddresses) => {
        bitcoinAddresses.date = bitcoinAddresses.date ? dayjs(bitcoinAddresses.date) : undefined;
      });
    }
    return res;
  }
}
