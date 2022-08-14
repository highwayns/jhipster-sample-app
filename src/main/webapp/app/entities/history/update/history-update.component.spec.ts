import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { HistoryService } from '../service/history.service';
import { IHistory, History } from '../history.model';
import { IHistoryActions } from 'app/entities/history-actions/history-actions.model';
import { HistoryActionsService } from 'app/entities/history-actions/service/history-actions.service';
import { IOrders } from 'app/entities/orders/orders.model';
import { OrdersService } from 'app/entities/orders/service/orders.service';
import { IRequests } from 'app/entities/requests/requests.model';
import { RequestsService } from 'app/entities/requests/service/requests.service';
import { ISiteUsers } from 'app/entities/site-users/site-users.model';
import { SiteUsersService } from 'app/entities/site-users/service/site-users.service';

import { HistoryUpdateComponent } from './history-update.component';

describe('History Management Update Component', () => {
  let comp: HistoryUpdateComponent;
  let fixture: ComponentFixture<HistoryUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let historyService: HistoryService;
  let historyActionsService: HistoryActionsService;
  let ordersService: OrdersService;
  let requestsService: RequestsService;
  let siteUsersService: SiteUsersService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [HistoryUpdateComponent],
      providers: [
        FormBuilder,
        {
          provide: ActivatedRoute,
          useValue: {
            params: from([{}]),
          },
        },
      ],
    })
      .overrideTemplate(HistoryUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(HistoryUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    historyService = TestBed.inject(HistoryService);
    historyActionsService = TestBed.inject(HistoryActionsService);
    ordersService = TestBed.inject(OrdersService);
    requestsService = TestBed.inject(RequestsService);
    siteUsersService = TestBed.inject(SiteUsersService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call historyAction query and add missing value', () => {
      const history: IHistory = { id: 456 };
      const historyAction: IHistoryActions = { id: 9223 };
      history.historyAction = historyAction;

      const historyActionCollection: IHistoryActions[] = [{ id: 4853 }];
      jest.spyOn(historyActionsService, 'query').mockReturnValue(of(new HttpResponse({ body: historyActionCollection })));
      const expectedCollection: IHistoryActions[] = [historyAction, ...historyActionCollection];
      jest.spyOn(historyActionsService, 'addHistoryActionsToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ history });
      comp.ngOnInit();

      expect(historyActionsService.query).toHaveBeenCalled();
      expect(historyActionsService.addHistoryActionsToCollectionIfMissing).toHaveBeenCalledWith(historyActionCollection, historyAction);
      expect(comp.historyActionsCollection).toEqual(expectedCollection);
    });

    it('Should call orderId query and add missing value', () => {
      const history: IHistory = { id: 456 };
      const orderId: IOrders = { id: 62235 };
      history.orderId = orderId;

      const orderIdCollection: IOrders[] = [{ id: 46505 }];
      jest.spyOn(ordersService, 'query').mockReturnValue(of(new HttpResponse({ body: orderIdCollection })));
      const expectedCollection: IOrders[] = [orderId, ...orderIdCollection];
      jest.spyOn(ordersService, 'addOrdersToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ history });
      comp.ngOnInit();

      expect(ordersService.query).toHaveBeenCalled();
      expect(ordersService.addOrdersToCollectionIfMissing).toHaveBeenCalledWith(orderIdCollection, orderId);
      expect(comp.orderIdsCollection).toEqual(expectedCollection);
    });

    it('Should call requestId query and add missing value', () => {
      const history: IHistory = { id: 456 };
      const requestId: IRequests = { id: 88335 };
      history.requestId = requestId;

      const requestIdCollection: IRequests[] = [{ id: 21969 }];
      jest.spyOn(requestsService, 'query').mockReturnValue(of(new HttpResponse({ body: requestIdCollection })));
      const expectedCollection: IRequests[] = [requestId, ...requestIdCollection];
      jest.spyOn(requestsService, 'addRequestsToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ history });
      comp.ngOnInit();

      expect(requestsService.query).toHaveBeenCalled();
      expect(requestsService.addRequestsToCollectionIfMissing).toHaveBeenCalledWith(requestIdCollection, requestId);
      expect(comp.requestIdsCollection).toEqual(expectedCollection);
    });

    it('Should call siteUser query and add missing value', () => {
      const history: IHistory = { id: 456 };
      const siteUser: ISiteUsers = { id: 6774 };
      history.siteUser = siteUser;

      const siteUserCollection: ISiteUsers[] = [{ id: 51056 }];
      jest.spyOn(siteUsersService, 'query').mockReturnValue(of(new HttpResponse({ body: siteUserCollection })));
      const expectedCollection: ISiteUsers[] = [siteUser, ...siteUserCollection];
      jest.spyOn(siteUsersService, 'addSiteUsersToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ history });
      comp.ngOnInit();

      expect(siteUsersService.query).toHaveBeenCalled();
      expect(siteUsersService.addSiteUsersToCollectionIfMissing).toHaveBeenCalledWith(siteUserCollection, siteUser);
      expect(comp.siteUsersCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const history: IHistory = { id: 456 };
      const historyAction: IHistoryActions = { id: 85617 };
      history.historyAction = historyAction;
      const orderId: IOrders = { id: 95452 };
      history.orderId = orderId;
      const requestId: IRequests = { id: 39410 };
      history.requestId = requestId;
      const siteUser: ISiteUsers = { id: 46601 };
      history.siteUser = siteUser;

      activatedRoute.data = of({ history });
      comp.ngOnInit();

      expect(comp.editForm.value).toEqual(expect.objectContaining(history));
      expect(comp.historyActionsCollection).toContain(historyAction);
      expect(comp.orderIdsCollection).toContain(orderId);
      expect(comp.requestIdsCollection).toContain(requestId);
      expect(comp.siteUsersCollection).toContain(siteUser);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<History>>();
      const history = { id: 123 };
      jest.spyOn(historyService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ history });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: history }));
      saveSubject.complete();

      // THEN
      expect(comp.previousState).toHaveBeenCalled();
      expect(historyService.update).toHaveBeenCalledWith(history);
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<History>>();
      const history = new History();
      jest.spyOn(historyService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ history });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: history }));
      saveSubject.complete();

      // THEN
      expect(historyService.create).toHaveBeenCalledWith(history);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<History>>();
      const history = { id: 123 };
      jest.spyOn(historyService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ history });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(historyService.update).toHaveBeenCalledWith(history);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Tracking relationships identifiers', () => {
    describe('trackHistoryActionsById', () => {
      it('Should return tracked HistoryActions primary key', () => {
        const entity = { id: 123 };
        const trackResult = comp.trackHistoryActionsById(0, entity);
        expect(trackResult).toEqual(entity.id);
      });
    });

    describe('trackOrdersById', () => {
      it('Should return tracked Orders primary key', () => {
        const entity = { id: 123 };
        const trackResult = comp.trackOrdersById(0, entity);
        expect(trackResult).toEqual(entity.id);
      });
    });

    describe('trackRequestsById', () => {
      it('Should return tracked Requests primary key', () => {
        const entity = { id: 123 };
        const trackResult = comp.trackRequestsById(0, entity);
        expect(trackResult).toEqual(entity.id);
      });
    });

    describe('trackSiteUsersById', () => {
      it('Should return tracked SiteUsers primary key', () => {
        const entity = { id: 123 };
        const trackResult = comp.trackSiteUsersById(0, entity);
        expect(trackResult).toEqual(entity.id);
      });
    });
  });
});
