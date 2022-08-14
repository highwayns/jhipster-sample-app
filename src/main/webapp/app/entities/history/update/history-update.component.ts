import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import dayjs from 'dayjs/esm';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';

import { IHistory, History } from '../history.model';
import { HistoryService } from '../service/history.service';
import { IHistoryActions } from 'app/entities/history-actions/history-actions.model';
import { HistoryActionsService } from 'app/entities/history-actions/service/history-actions.service';
import { IOrders } from 'app/entities/orders/orders.model';
import { OrdersService } from 'app/entities/orders/service/orders.service';
import { IRequests } from 'app/entities/requests/requests.model';
import { RequestsService } from 'app/entities/requests/service/requests.service';
import { ISiteUsers } from 'app/entities/site-users/site-users.model';
import { SiteUsersService } from 'app/entities/site-users/service/site-users.service';

@Component({
  selector: 'jhi-history-update',
  templateUrl: './history-update.component.html',
})
export class HistoryUpdateComponent implements OnInit {
  isSaving = false;

  historyActionsCollection: IHistoryActions[] = [];
  orderIdsCollection: IOrders[] = [];
  requestIdsCollection: IRequests[] = [];
  siteUsersCollection: ISiteUsers[] = [];

  editForm = this.fb.group({
    id: [],
    date: [null, [Validators.required]],
    ip: [null, [Validators.required, Validators.maxLength(255)]],
    bitcoinAddress: [null, [Validators.required, Validators.maxLength(255)]],
    balanceBefore: [null, [Validators.required]],
    balanceAfter: [null, [Validators.required]],
    historyAction: [],
    orderId: [],
    requestId: [],
    siteUser: [],
  });

  constructor(
    protected historyService: HistoryService,
    protected historyActionsService: HistoryActionsService,
    protected ordersService: OrdersService,
    protected requestsService: RequestsService,
    protected siteUsersService: SiteUsersService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ history }) => {
      if (history.id === undefined) {
        const today = dayjs().startOf('day');
        history.date = today;
      }

      this.updateForm(history);

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const history = this.createFromForm();
    if (history.id !== undefined) {
      this.subscribeToSaveResponse(this.historyService.update(history));
    } else {
      this.subscribeToSaveResponse(this.historyService.create(history));
    }
  }

  trackHistoryActionsById(_index: number, item: IHistoryActions): number {
    return item.id!;
  }

  trackOrdersById(_index: number, item: IOrders): number {
    return item.id!;
  }

  trackRequestsById(_index: number, item: IRequests): number {
    return item.id!;
  }

  trackSiteUsersById(_index: number, item: ISiteUsers): number {
    return item.id!;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IHistory>>): void {
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

  protected updateForm(history: IHistory): void {
    this.editForm.patchValue({
      id: history.id,
      date: history.date ? history.date.format(DATE_TIME_FORMAT) : null,
      ip: history.ip,
      bitcoinAddress: history.bitcoinAddress,
      balanceBefore: history.balanceBefore,
      balanceAfter: history.balanceAfter,
      historyAction: history.historyAction,
      orderId: history.orderId,
      requestId: history.requestId,
      siteUser: history.siteUser,
    });

    this.historyActionsCollection = this.historyActionsService.addHistoryActionsToCollectionIfMissing(
      this.historyActionsCollection,
      history.historyAction
    );
    this.orderIdsCollection = this.ordersService.addOrdersToCollectionIfMissing(this.orderIdsCollection, history.orderId);
    this.requestIdsCollection = this.requestsService.addRequestsToCollectionIfMissing(this.requestIdsCollection, history.requestId);
    this.siteUsersCollection = this.siteUsersService.addSiteUsersToCollectionIfMissing(this.siteUsersCollection, history.siteUser);
  }

  protected loadRelationshipsOptions(): void {
    this.historyActionsService
      .query({ filter: 'history-is-null' })
      .pipe(map((res: HttpResponse<IHistoryActions[]>) => res.body ?? []))
      .pipe(
        map((historyActions: IHistoryActions[]) =>
          this.historyActionsService.addHistoryActionsToCollectionIfMissing(historyActions, this.editForm.get('historyAction')!.value)
        )
      )
      .subscribe((historyActions: IHistoryActions[]) => (this.historyActionsCollection = historyActions));

    this.ordersService
      .query({ filter: 'history-is-null' })
      .pipe(map((res: HttpResponse<IOrders[]>) => res.body ?? []))
      .pipe(map((orders: IOrders[]) => this.ordersService.addOrdersToCollectionIfMissing(orders, this.editForm.get('orderId')!.value)))
      .subscribe((orders: IOrders[]) => (this.orderIdsCollection = orders));

    this.requestsService
      .query({ filter: 'history-is-null' })
      .pipe(map((res: HttpResponse<IRequests[]>) => res.body ?? []))
      .pipe(
        map((requests: IRequests[]) =>
          this.requestsService.addRequestsToCollectionIfMissing(requests, this.editForm.get('requestId')!.value)
        )
      )
      .subscribe((requests: IRequests[]) => (this.requestIdsCollection = requests));

    this.siteUsersService
      .query({ filter: 'history-is-null' })
      .pipe(map((res: HttpResponse<ISiteUsers[]>) => res.body ?? []))
      .pipe(
        map((siteUsers: ISiteUsers[]) =>
          this.siteUsersService.addSiteUsersToCollectionIfMissing(siteUsers, this.editForm.get('siteUser')!.value)
        )
      )
      .subscribe((siteUsers: ISiteUsers[]) => (this.siteUsersCollection = siteUsers));
  }

  protected createFromForm(): IHistory {
    return {
      ...new History(),
      id: this.editForm.get(['id'])!.value,
      date: this.editForm.get(['date'])!.value ? dayjs(this.editForm.get(['date'])!.value, DATE_TIME_FORMAT) : undefined,
      ip: this.editForm.get(['ip'])!.value,
      bitcoinAddress: this.editForm.get(['bitcoinAddress'])!.value,
      balanceBefore: this.editForm.get(['balanceBefore'])!.value,
      balanceAfter: this.editForm.get(['balanceAfter'])!.value,
      historyAction: this.editForm.get(['historyAction'])!.value,
      orderId: this.editForm.get(['orderId'])!.value,
      requestId: this.editForm.get(['requestId'])!.value,
      siteUser: this.editForm.get(['siteUser'])!.value,
    };
  }
}
