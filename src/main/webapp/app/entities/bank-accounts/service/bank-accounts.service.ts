import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IBankAccounts, getBankAccountsIdentifier } from '../bank-accounts.model';

export type EntityResponseType = HttpResponse<IBankAccounts>;
export type EntityArrayResponseType = HttpResponse<IBankAccounts[]>;

@Injectable({ providedIn: 'root' })
export class BankAccountsService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/bank-accounts');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(bankAccounts: IBankAccounts): Observable<EntityResponseType> {
    return this.http.post<IBankAccounts>(this.resourceUrl, bankAccounts, { observe: 'response' });
  }

  update(bankAccounts: IBankAccounts): Observable<EntityResponseType> {
    return this.http.put<IBankAccounts>(`${this.resourceUrl}/${getBankAccountsIdentifier(bankAccounts) as number}`, bankAccounts, {
      observe: 'response',
    });
  }

  partialUpdate(bankAccounts: IBankAccounts): Observable<EntityResponseType> {
    return this.http.patch<IBankAccounts>(`${this.resourceUrl}/${getBankAccountsIdentifier(bankAccounts) as number}`, bankAccounts, {
      observe: 'response',
    });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IBankAccounts>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IBankAccounts[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addBankAccountsToCollectionIfMissing(
    bankAccountsCollection: IBankAccounts[],
    ...bankAccountsToCheck: (IBankAccounts | null | undefined)[]
  ): IBankAccounts[] {
    const bankAccounts: IBankAccounts[] = bankAccountsToCheck.filter(isPresent);
    if (bankAccounts.length > 0) {
      const bankAccountsCollectionIdentifiers = bankAccountsCollection.map(
        bankAccountsItem => getBankAccountsIdentifier(bankAccountsItem)!
      );
      const bankAccountsToAdd = bankAccounts.filter(bankAccountsItem => {
        const bankAccountsIdentifier = getBankAccountsIdentifier(bankAccountsItem);
        if (bankAccountsIdentifier == null || bankAccountsCollectionIdentifiers.includes(bankAccountsIdentifier)) {
          return false;
        }
        bankAccountsCollectionIdentifiers.push(bankAccountsIdentifier);
        return true;
      });
      return [...bankAccountsToAdd, ...bankAccountsCollection];
    }
    return bankAccountsCollection;
  }
}
