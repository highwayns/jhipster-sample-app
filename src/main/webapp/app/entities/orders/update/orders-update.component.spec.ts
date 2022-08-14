import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { OrdersService } from '../service/orders.service';
import { IOrders, Orders } from '../orders.model';
import { IOrderTypes } from 'app/entities/order-types/order-types.model';
import { OrderTypesService } from 'app/entities/order-types/service/order-types.service';
import { ISiteUsers } from 'app/entities/site-users/site-users.model';
import { SiteUsersService } from 'app/entities/site-users/service/site-users.service';
import { ICurrencies } from 'app/entities/currencies/currencies.model';
import { CurrenciesService } from 'app/entities/currencies/service/currencies.service';
import { IOrderLog } from 'app/entities/order-log/order-log.model';
import { OrderLogService } from 'app/entities/order-log/service/order-log.service';

import { OrdersUpdateComponent } from './orders-update.component';

describe('Orders Management Update Component', () => {
  let comp: OrdersUpdateComponent;
  let fixture: ComponentFixture<OrdersUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let ordersService: OrdersService;
  let orderTypesService: OrderTypesService;
  let siteUsersService: SiteUsersService;
  let currenciesService: CurrenciesService;
  let orderLogService: OrderLogService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [OrdersUpdateComponent],
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
      .overrideTemplate(OrdersUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(OrdersUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    ordersService = TestBed.inject(OrdersService);
    orderTypesService = TestBed.inject(OrderTypesService);
    siteUsersService = TestBed.inject(SiteUsersService);
    currenciesService = TestBed.inject(CurrenciesService);
    orderLogService = TestBed.inject(OrderLogService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call orderType query and add missing value', () => {
      const orders: IOrders = { id: 456 };
      const orderType: IOrderTypes = { id: 85900 };
      orders.orderType = orderType;

      const orderTypeCollection: IOrderTypes[] = [{ id: 72635 }];
      jest.spyOn(orderTypesService, 'query').mockReturnValue(of(new HttpResponse({ body: orderTypeCollection })));
      const expectedCollection: IOrderTypes[] = [orderType, ...orderTypeCollection];
      jest.spyOn(orderTypesService, 'addOrderTypesToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ orders });
      comp.ngOnInit();

      expect(orderTypesService.query).toHaveBeenCalled();
      expect(orderTypesService.addOrderTypesToCollectionIfMissing).toHaveBeenCalledWith(orderTypeCollection, orderType);
      expect(comp.orderTypesCollection).toEqual(expectedCollection);
    });

    it('Should call siteUser query and add missing value', () => {
      const orders: IOrders = { id: 456 };
      const siteUser: ISiteUsers = { id: 34369 };
      orders.siteUser = siteUser;

      const siteUserCollection: ISiteUsers[] = [{ id: 80077 }];
      jest.spyOn(siteUsersService, 'query').mockReturnValue(of(new HttpResponse({ body: siteUserCollection })));
      const expectedCollection: ISiteUsers[] = [siteUser, ...siteUserCollection];
      jest.spyOn(siteUsersService, 'addSiteUsersToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ orders });
      comp.ngOnInit();

      expect(siteUsersService.query).toHaveBeenCalled();
      expect(siteUsersService.addSiteUsersToCollectionIfMissing).toHaveBeenCalledWith(siteUserCollection, siteUser);
      expect(comp.siteUsersCollection).toEqual(expectedCollection);
    });

    it('Should call currency query and add missing value', () => {
      const orders: IOrders = { id: 456 };
      const currency: ICurrencies = { id: 99754 };
      orders.currency = currency;

      const currencyCollection: ICurrencies[] = [{ id: 27189 }];
      jest.spyOn(currenciesService, 'query').mockReturnValue(of(new HttpResponse({ body: currencyCollection })));
      const expectedCollection: ICurrencies[] = [currency, ...currencyCollection];
      jest.spyOn(currenciesService, 'addCurrenciesToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ orders });
      comp.ngOnInit();

      expect(currenciesService.query).toHaveBeenCalled();
      expect(currenciesService.addCurrenciesToCollectionIfMissing).toHaveBeenCalledWith(currencyCollection, currency);
      expect(comp.currenciesCollection).toEqual(expectedCollection);
    });

    it('Should call logId query and add missing value', () => {
      const orders: IOrders = { id: 456 };
      const logId: IOrderLog = { id: 77630 };
      orders.logId = logId;

      const logIdCollection: IOrderLog[] = [{ id: 96323 }];
      jest.spyOn(orderLogService, 'query').mockReturnValue(of(new HttpResponse({ body: logIdCollection })));
      const expectedCollection: IOrderLog[] = [logId, ...logIdCollection];
      jest.spyOn(orderLogService, 'addOrderLogToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ orders });
      comp.ngOnInit();

      expect(orderLogService.query).toHaveBeenCalled();
      expect(orderLogService.addOrderLogToCollectionIfMissing).toHaveBeenCalledWith(logIdCollection, logId);
      expect(comp.logIdsCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const orders: IOrders = { id: 456 };
      const orderType: IOrderTypes = { id: 10204 };
      orders.orderType = orderType;
      const siteUser: ISiteUsers = { id: 90908 };
      orders.siteUser = siteUser;
      const currency: ICurrencies = { id: 55339 };
      orders.currency = currency;
      const logId: IOrderLog = { id: 39679 };
      orders.logId = logId;

      activatedRoute.data = of({ orders });
      comp.ngOnInit();

      expect(comp.editForm.value).toEqual(expect.objectContaining(orders));
      expect(comp.orderTypesCollection).toContain(orderType);
      expect(comp.siteUsersCollection).toContain(siteUser);
      expect(comp.currenciesCollection).toContain(currency);
      expect(comp.logIdsCollection).toContain(logId);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Orders>>();
      const orders = { id: 123 };
      jest.spyOn(ordersService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ orders });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: orders }));
      saveSubject.complete();

      // THEN
      expect(comp.previousState).toHaveBeenCalled();
      expect(ordersService.update).toHaveBeenCalledWith(orders);
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Orders>>();
      const orders = new Orders();
      jest.spyOn(ordersService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ orders });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: orders }));
      saveSubject.complete();

      // THEN
      expect(ordersService.create).toHaveBeenCalledWith(orders);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Orders>>();
      const orders = { id: 123 };
      jest.spyOn(ordersService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ orders });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(ordersService.update).toHaveBeenCalledWith(orders);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Tracking relationships identifiers', () => {
    describe('trackOrderTypesById', () => {
      it('Should return tracked OrderTypes primary key', () => {
        const entity = { id: 123 };
        const trackResult = comp.trackOrderTypesById(0, entity);
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

    describe('trackCurrenciesById', () => {
      it('Should return tracked Currencies primary key', () => {
        const entity = { id: 123 };
        const trackResult = comp.trackCurrenciesById(0, entity);
        expect(trackResult).toEqual(entity.id);
      });
    });

    describe('trackOrderLogById', () => {
      it('Should return tracked OrderLog primary key', () => {
        const entity = { id: 123 };
        const trackResult = comp.trackOrderLogById(0, entity);
        expect(trackResult).toEqual(entity.id);
      });
    });
  });
});
