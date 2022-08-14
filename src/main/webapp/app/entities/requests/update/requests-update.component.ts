import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import dayjs from 'dayjs/esm';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';

import { IRequests, Requests } from '../requests.model';
import { RequestsService } from '../service/requests.service';
import { ISiteUsers } from 'app/entities/site-users/site-users.model';
import { SiteUsersService } from 'app/entities/site-users/service/site-users.service';
import { ICurrencies } from 'app/entities/currencies/currencies.model';
import { CurrenciesService } from 'app/entities/currencies/service/currencies.service';
import { IRequestDescriptions } from 'app/entities/request-descriptions/request-descriptions.model';
import { RequestDescriptionsService } from 'app/entities/request-descriptions/service/request-descriptions.service';
import { IRequestStatus } from 'app/entities/request-status/request-status.model';
import { RequestStatusService } from 'app/entities/request-status/service/request-status.service';
import { IRequestTypes } from 'app/entities/request-types/request-types.model';
import { RequestTypesService } from 'app/entities/request-types/service/request-types.service';
import { YesNo } from 'app/entities/enumerations/yes-no.model';

@Component({
  selector: 'jhi-requests-update',
  templateUrl: './requests-update.component.html',
})
export class RequestsUpdateComponent implements OnInit {
  isSaving = false;
  yesNoValues = Object.keys(YesNo);

  siteUsersCollection: ISiteUsers[] = [];
  currenciesCollection: ICurrencies[] = [];
  descriptionsCollection: IRequestDescriptions[] = [];
  requestStatusesCollection: IRequestStatus[] = [];
  requestTypesCollection: IRequestTypes[] = [];

  editForm = this.fb.group({
    id: [],
    date: [null, [Validators.required]],
    amount: [null, [Validators.required]],
    addressId: [null, [Validators.required]],
    account: [null, [Validators.required]],
    sendAddress: [null, [Validators.required, Validators.maxLength(255)]],
    transactionId: [null, [Validators.required, Validators.maxLength(255)]],
    increment: [null, [Validators.required]],
    done: [null, [Validators.required]],
    cryptoId: [null, [Validators.required]],
    fee: [null, [Validators.required]],
    netAmount: [null, [Validators.required]],
    notified: [null, [Validators.required]],
    siteUser: [],
    currency: [],
    description: [],
    requestStatus: [],
    requestType: [],
  });

  constructor(
    protected requestsService: RequestsService,
    protected siteUsersService: SiteUsersService,
    protected currenciesService: CurrenciesService,
    protected requestDescriptionsService: RequestDescriptionsService,
    protected requestStatusService: RequestStatusService,
    protected requestTypesService: RequestTypesService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ requests }) => {
      if (requests.id === undefined) {
        const today = dayjs().startOf('day');
        requests.date = today;
      }

      this.updateForm(requests);

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const requests = this.createFromForm();
    if (requests.id !== undefined) {
      this.subscribeToSaveResponse(this.requestsService.update(requests));
    } else {
      this.subscribeToSaveResponse(this.requestsService.create(requests));
    }
  }

  trackSiteUsersById(_index: number, item: ISiteUsers): number {
    return item.id!;
  }

  trackCurrenciesById(_index: number, item: ICurrencies): number {
    return item.id!;
  }

  trackRequestDescriptionsById(_index: number, item: IRequestDescriptions): number {
    return item.id!;
  }

  trackRequestStatusById(_index: number, item: IRequestStatus): number {
    return item.id!;
  }

  trackRequestTypesById(_index: number, item: IRequestTypes): number {
    return item.id!;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IRequests>>): void {
    result.pipe(finalize(() => this.onSaveFinalize())).subscribe({
      next: () => this.onSaveSuccess(),
      error: () => this.onSaveError(),
    });
  }

  protected onSaveSuccess(): void {
    this.previousState();
  }

  protected onSaveError(): void {
    // Api for inheritance.
  }

  protected onSaveFinalize(): void {
    this.isSaving = false;
  }

  protected updateForm(requests: IRequests): void {
    this.editForm.patchValue({
      id: requests.id,
      date: requests.date ? requests.date.format(DATE_TIME_FORMAT) : null,
      amount: requests.amount,
      addressId: requests.addressId,
      account: requests.account,
      sendAddress: requests.sendAddress,
      transactionId: requests.transactionId,
      increment: requests.increment,
      done: requests.done,
      cryptoId: requests.cryptoId,
      fee: requests.fee,
      netAmount: requests.netAmount,
      notified: requests.notified,
      siteUser: requests.siteUser,
      currency: requests.currency,
      description: requests.description,
      requestStatus: requests.requestStatus,
      requestType: requests.requestType,
    });

    this.siteUsersCollection = this.siteUsersService.addSiteUsersToCollectionIfMissing(this.siteUsersCollection, requests.siteUser);
    this.currenciesCollection = this.currenciesService.addCurrenciesToCollectionIfMissing(this.currenciesCollection, requests.currency);
    this.descriptionsCollection = this.requestDescriptionsService.addRequestDescriptionsToCollectionIfMissing(
      this.descriptionsCollection,
      requests.description
    );
    this.requestStatusesCollection = this.requestStatusService.addRequestStatusToCollectionIfMissing(
      this.requestStatusesCollection,
      requests.requestStatus
    );
    this.requestTypesCollection = this.requestTypesService.addRequestTypesToCollectionIfMissing(
      this.requestTypesCollection,
      requests.requestType
    );
  }

  protected loadRelationshipsOptions(): void {
    this.siteUsersService
      .query({ filter: 'requests-is-null' })
      .pipe(map((res: HttpResponse<ISiteUsers[]>) => res.body ?? []))
      .pipe(
        map((siteUsers: ISiteUsers[]) =>
          this.siteUsersService.addSiteUsersToCollectionIfMissing(siteUsers, this.editForm.get('siteUser')!.value)
        )
      )
      .subscribe((siteUsers: ISiteUsers[]) => (this.siteUsersCollection = siteUsers));

    this.currenciesService
      .query({ filter: 'requests-is-null' })
      .pipe(map((res: HttpResponse<ICurrencies[]>) => res.body ?? []))
      .pipe(
        map((currencies: ICurrencies[]) =>
          this.currenciesService.addCurrenciesToCollectionIfMissing(currencies, this.editForm.get('currency')!.value)
        )
      )
      .subscribe((currencies: ICurrencies[]) => (this.currenciesCollection = currencies));

    this.requestDescriptionsService
      .query({ filter: 'requests-is-null' })
      .pipe(map((res: HttpResponse<IRequestDescriptions[]>) => res.body ?? []))
      .pipe(
        map((requestDescriptions: IRequestDescriptions[]) =>
          this.requestDescriptionsService.addRequestDescriptionsToCollectionIfMissing(
            requestDescriptions,
            this.editForm.get('description')!.value
          )
        )
      )
      .subscribe((requestDescriptions: IRequestDescriptions[]) => (this.descriptionsCollection = requestDescriptions));

    this.requestStatusService
      .query({ filter: 'requests-is-null' })
      .pipe(map((res: HttpResponse<IRequestStatus[]>) => res.body ?? []))
      .pipe(
        map((requestStatuses: IRequestStatus[]) =>
          this.requestStatusService.addRequestStatusToCollectionIfMissing(requestStatuses, this.editForm.get('requestStatus')!.value)
        )
      )
      .subscribe((requestStatuses: IRequestStatus[]) => (this.requestStatusesCollection = requestStatuses));

    this.requestTypesService
      .query({ filter: 'requests-is-null' })
      .pipe(map((res: HttpResponse<IRequestTypes[]>) => res.body ?? []))
      .pipe(
        map((requestTypes: IRequestTypes[]) =>
          this.requestTypesService.addRequestTypesToCollectionIfMissing(requestTypes, this.editForm.get('requestType')!.value)
        )
      )
      .subscribe((requestTypes: IRequestTypes[]) => (this.requestTypesCollection = requestTypes));
  }

  protected createFromForm(): IRequests {
    return {
      ...new Requests(),
      id: this.editForm.get(['id'])!.value,
      date: this.editForm.get(['date'])!.value ? dayjs(this.editForm.get(['date'])!.value, DATE_TIME_FORMAT) : undefined,
      amount: this.editForm.get(['amount'])!.value,
      addressId: this.editForm.get(['addressId'])!.value,
      account: this.editForm.get(['account'])!.value,
      sendAddress: this.editForm.get(['sendAddress'])!.value,
      transactionId: this.editForm.get(['transactionId'])!.value,
      increment: this.editForm.get(['increment'])!.value,
      done: this.editForm.get(['done'])!.value,
      cryptoId: this.editForm.get(['cryptoId'])!.value,
      fee: this.editForm.get(['fee'])!.value,
      netAmount: this.editForm.get(['netAmount'])!.value,
      notified: this.editForm.get(['notified'])!.value,
      siteUser: this.editForm.get(['siteUser'])!.value,
      currency: this.editForm.get(['currency'])!.value,
      description: this.editForm.get(['description'])!.value,
      requestStatus: this.editForm.get(['requestStatus'])!.value,
      requestType: this.editForm.get(['requestType'])!.value,
    };
  }
}
