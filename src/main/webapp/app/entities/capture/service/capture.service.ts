import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import dayjs from 'dayjs/esm';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { ICapture, getCaptureIdentifier } from '../capture.model';

export type EntityResponseType = HttpResponse<ICapture>;
export type EntityArrayResponseType = HttpResponse<ICapture[]>;

@Injectable({ providedIn: 'root' })
export class CaptureService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/captures');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(capture: ICapture): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(capture);
    return this.http
      .post<ICapture>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(capture: ICapture): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(capture);
    return this.http
      .put<ICapture>(`${this.resourceUrl}/${getCaptureIdentifier(capture) as number}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  partialUpdate(capture: ICapture): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(capture);
    return this.http
      .patch<ICapture>(`${this.resourceUrl}/${getCaptureIdentifier(capture) as number}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<ICapture>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<ICapture[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addCaptureToCollectionIfMissing(captureCollection: ICapture[], ...capturesToCheck: (ICapture | null | undefined)[]): ICapture[] {
    const captures: ICapture[] = capturesToCheck.filter(isPresent);
    if (captures.length > 0) {
      const captureCollectionIdentifiers = captureCollection.map(captureItem => getCaptureIdentifier(captureItem)!);
      const capturesToAdd = captures.filter(captureItem => {
        const captureIdentifier = getCaptureIdentifier(captureItem);
        if (captureIdentifier == null || captureCollectionIdentifiers.includes(captureIdentifier)) {
          return false;
        }
        captureCollectionIdentifiers.push(captureIdentifier);
        return true;
      });
      return [...capturesToAdd, ...captureCollection];
    }
    return captureCollection;
  }

  protected convertDateFromClient(capture: ICapture): ICapture {
    return Object.assign({}, capture, {
      createDateTimeUtc: capture.createDateTimeUtc?.isValid() ? capture.createDateTimeUtc.toJSON() : undefined,
    });
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.createDateTimeUtc = res.body.createDateTimeUtc ? dayjs(res.body.createDateTimeUtc) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((capture: ICapture) => {
        capture.createDateTimeUtc = capture.createDateTimeUtc ? dayjs(capture.createDateTimeUtc) : undefined;
      });
    }
    return res;
  }
}
