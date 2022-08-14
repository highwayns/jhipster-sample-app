import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { OrderLogService } from '../service/order-log.service';
import { IOrderLog, OrderLog } from '../order-log.model';
import { ISiteUsers } from 'app/entities/site-users/site-users.model';
import { SiteUsersService } from 'app/entities/site-users/service/site-users.service';
import { ICurrencies } from 'app/entities/currencies/currencies.model';
import { CurrenciesService } from 'app/entities/currencies/service/currencies.service';
import { IOrderTypes } from 'app/entities/order-types/order-types.model';
import { OrderTypesService } from 'app/entities/order-types/service/order-types.service';

import { OrderLogUpdateComponent } from './order-log-update.component';

describe('OrderLog Management Update Component', () => {
  let comp: OrderLogUpdateComponent;
  let fixture: ComponentFixture<OrderLogUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let orderLogService: OrderLogService;
  let siteUsersService: SiteUsersService;
  let currenciesService: CurrenciesService;
  let orderTypesService: OrderTypesService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [OrderLogUpdateComponent],
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
      .overrideTemplate(OrderLogUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(OrderLogUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    orderLogService = TestBed.inject(OrderLogService);
    siteUsersService = TestBed.inject(SiteUsersService);
    currenciesService = TestBed.inject(CurrenciesService);
    orderTypesService = TestBed.inject(OrderTypesService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call siteUser query and add missing value', () => {
      const orderLog: IOrderLog = { id: 456 };
      const siteUser: ISiteUsers = { id: 39383 };
      orderLog.siteUser = siteUser;

      const siteUserCollection: ISiteUsers[] = [{ id: 7501 }];
      jest.spyOn(siteUsersService, 'query').mockReturnValue(of(new HttpResponse({ body: siteUserCollection })));
      const expectedCollection: ISiteUsers[] = [siteUser, ...siteUserCollection];
      jest.spyOn(siteUsersService, 'addSiteUsersToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ orderLog });
      comp.ngOnInit();

      expect(siteUsersService.query).toHaveBeenCalled();
      expect(siteUsersService.addSiteUsersToCollectionIfMissing).toHaveBeenCalledWith(siteUserCollection, siteUser);
      expect(comp.siteUsersCollection).toEqual(expectedCollection);
    });

    it('Should call currency query and add missing value', () => {
      const orderLog: IOrderLog = { id: 456 };
      const currency: ICurrencies = { id: 44667 };
      orderLog.currency = currency;

      const currencyCollection: ICurrencies[] = [{ id: 98458 }];
      jest.spyOn(currenciesService, 'query').mockReturnValue(of(new HttpResponse({ body: currencyCollection })));
      const expectedCollection: ICurrencies[] = [currency, ...currencyCollection];
      jest.spyOn(currenciesService, 'addCurrenciesToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ orderLog });
      comp.ngOnInit();

      expect(currenciesService.query).toHaveBeenCalled();
      expect(currenciesService.addCurrenciesToCollectionIfMissing).toHaveBeenCalledWith(currencyCollection, currency);
      expect(comp.currenciesCollection).toEqual(expectedCollection);
    });

    it('Should call orderType query and add missing value', () => {
      const orderLog: IOrderLog = { id: 456 };
      const orderType: IOrderTypes = { id: 53496 };
      orderLog.orderType = orderType;

      const orderTypeCollection: IOrderTypes[] = [{ id: 43858 }];
      jest.spyOn(orderTypesService, 'query').mockReturnValue(of(new HttpResponse({ body: orderTypeCollection })));
      const expectedCollection: IOrderTypes[] = [orderType, ...orderTypeCollection];
      jest.spyOn(orderTypesService, 'addOrderTypesToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ orderLog });
      comp.ngOnInit();

      expect(orderTypesService.query).toHaveBeenCalled();
      expect(orderTypesService.addOrderTypesToCollectionIfMissing).toHaveBeenCalledWith(orderTypeCollection, orderType);
      expect(comp.orderTypesCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const orderLog: IOrderLog = { id: 456 };
      const siteUser: ISiteUsers = { id: 81952 };
      orderLog.siteUser = siteUser;
      const currency: ICurrencies = { id: 51374 };
      orderLog.currency = currency;
      const orderType: IOrderTypes = { id: 74446 };
      orderLog.orderType = orderType;

      activatedRoute.data = of({ orderLog });
      comp.ngOnInit();

      expect(comp.editForm.value).toEqual(expect.objectContaining(orderLog));
      expect(comp.siteUsersCollection).toContain(siteUser);
      expect(comp.currenciesCollection).toContain(currency);
      expect(comp.orderTypesCollection).toContain(orderType);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<OrderLog>>();
      const orderLog = { id: 123 };
      jest.spyOn(orderLogService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ orderLog });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: orderLog }));
      saveSubject.complete();

      // THEN
      expect(comp.previousState).toHaveBeenCalled();
      expect(orderLogService.update).toHaveBeenCalledWith(orderLog);
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<OrderLog>>();
      const orderLog = new OrderLog();
      jest.spyOn(orderLogService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ orderLog });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: orderLog }));
      saveSubject.complete();

      // THEN
      expect(orderLogService.create).toHaveBeenCalledWith(orderLog);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<OrderLog>>();
      const orderLog = { id: 123 };
      jest.spyOn(orderLogService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ orderLog });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(orderLogService.update).toHaveBeenCalledWith(orderLog);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Tracking relationships identifiers', () => {
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

    describe('trackOrderTypesById', () => {
      it('Should return tracked OrderTypes primary key', () => {
        const entity = { id: 123 };
        const trackResult = comp.trackOrderTypesById(0, entity);
        expect(trackResult).toEqual(entity.id);
      });
    });
  });
});
