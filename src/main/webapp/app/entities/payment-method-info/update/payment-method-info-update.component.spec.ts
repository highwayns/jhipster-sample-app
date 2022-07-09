import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { PaymentMethodInfoService } from '../service/payment-method-info.service';
import { IPaymentMethodInfo, PaymentMethodInfo } from '../payment-method-info.model';
import { ICurrencys } from 'app/entities/currencys/currencys.model';
import { CurrencysService } from 'app/entities/currencys/service/currencys.service';
import { IIssuer } from 'app/entities/issuer/issuer.model';
import { IssuerService } from 'app/entities/issuer/service/issuer.service';
import { ICardTokenData } from 'app/entities/card-token-data/card-token-data.model';
import { CardTokenDataService } from 'app/entities/card-token-data/service/card-token-data.service';

import { PaymentMethodInfoUpdateComponent } from './payment-method-info-update.component';

describe('PaymentMethodInfo Management Update Component', () => {
  let comp: PaymentMethodInfoUpdateComponent;
  let fixture: ComponentFixture<PaymentMethodInfoUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let paymentMethodInfoService: PaymentMethodInfoService;
  let currencysService: CurrencysService;
  let issuerService: IssuerService;
  let cardTokenDataService: CardTokenDataService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [PaymentMethodInfoUpdateComponent],
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
      .overrideTemplate(PaymentMethodInfoUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(PaymentMethodInfoUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    paymentMethodInfoService = TestBed.inject(PaymentMethodInfoService);
    currencysService = TestBed.inject(CurrencysService);
    issuerService = TestBed.inject(IssuerService);
    cardTokenDataService = TestBed.inject(CardTokenDataService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call Currencys query and add missing value', () => {
      const paymentMethodInfo: IPaymentMethodInfo = { id: 456 };
      const currencies: ICurrencys = { id: 15218 };
      paymentMethodInfo.currencies = currencies;

      const currencysCollection: ICurrencys[] = [{ id: 75480 }];
      jest.spyOn(currencysService, 'query').mockReturnValue(of(new HttpResponse({ body: currencysCollection })));
      const additionalCurrencys = [currencies];
      const expectedCollection: ICurrencys[] = [...additionalCurrencys, ...currencysCollection];
      jest.spyOn(currencysService, 'addCurrencysToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ paymentMethodInfo });
      comp.ngOnInit();

      expect(currencysService.query).toHaveBeenCalled();
      expect(currencysService.addCurrencysToCollectionIfMissing).toHaveBeenCalledWith(currencysCollection, ...additionalCurrencys);
      expect(comp.currencysSharedCollection).toEqual(expectedCollection);
    });

    it('Should call Issuer query and add missing value', () => {
      const paymentMethodInfo: IPaymentMethodInfo = { id: 456 };
      const issuerList: IIssuer = { id: '630cad32-858d-4353-b78f-01a58b74d0b6' };
      paymentMethodInfo.issuerList = issuerList;

      const issuerCollection: IIssuer[] = [{ id: '514ed015-fff0-4aed-8a28-02e23df62ecf' }];
      jest.spyOn(issuerService, 'query').mockReturnValue(of(new HttpResponse({ body: issuerCollection })));
      const additionalIssuers = [issuerList];
      const expectedCollection: IIssuer[] = [...additionalIssuers, ...issuerCollection];
      jest.spyOn(issuerService, 'addIssuerToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ paymentMethodInfo });
      comp.ngOnInit();

      expect(issuerService.query).toHaveBeenCalled();
      expect(issuerService.addIssuerToCollectionIfMissing).toHaveBeenCalledWith(issuerCollection, ...additionalIssuers);
      expect(comp.issuersSharedCollection).toEqual(expectedCollection);
    });

    it('Should call CardTokenData query and add missing value', () => {
      const paymentMethodInfo: IPaymentMethodInfo = { id: 456 };
      const tokenizedCards: ICardTokenData = { id: 19908 };
      paymentMethodInfo.tokenizedCards = tokenizedCards;

      const cardTokenDataCollection: ICardTokenData[] = [{ id: 18628 }];
      jest.spyOn(cardTokenDataService, 'query').mockReturnValue(of(new HttpResponse({ body: cardTokenDataCollection })));
      const additionalCardTokenData = [tokenizedCards];
      const expectedCollection: ICardTokenData[] = [...additionalCardTokenData, ...cardTokenDataCollection];
      jest.spyOn(cardTokenDataService, 'addCardTokenDataToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ paymentMethodInfo });
      comp.ngOnInit();

      expect(cardTokenDataService.query).toHaveBeenCalled();
      expect(cardTokenDataService.addCardTokenDataToCollectionIfMissing).toHaveBeenCalledWith(
        cardTokenDataCollection,
        ...additionalCardTokenData
      );
      expect(comp.cardTokenDataSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const paymentMethodInfo: IPaymentMethodInfo = { id: 456 };
      const currencies: ICurrencys = { id: 10896 };
      paymentMethodInfo.currencies = currencies;
      const issuerList: IIssuer = { id: '55998970-b02a-4cfc-ba5d-fdb04eac6ebd' };
      paymentMethodInfo.issuerList = issuerList;
      const tokenizedCards: ICardTokenData = { id: 44724 };
      paymentMethodInfo.tokenizedCards = tokenizedCards;

      activatedRoute.data = of({ paymentMethodInfo });
      comp.ngOnInit();

      expect(comp.editForm.value).toEqual(expect.objectContaining(paymentMethodInfo));
      expect(comp.currencysSharedCollection).toContain(currencies);
      expect(comp.issuersSharedCollection).toContain(issuerList);
      expect(comp.cardTokenDataSharedCollection).toContain(tokenizedCards);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<PaymentMethodInfo>>();
      const paymentMethodInfo = { id: 123 };
      jest.spyOn(paymentMethodInfoService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ paymentMethodInfo });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: paymentMethodInfo }));
      saveSubject.complete();

      // THEN
      expect(comp.previousState).toHaveBeenCalled();
      expect(paymentMethodInfoService.update).toHaveBeenCalledWith(paymentMethodInfo);
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<PaymentMethodInfo>>();
      const paymentMethodInfo = new PaymentMethodInfo();
      jest.spyOn(paymentMethodInfoService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ paymentMethodInfo });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: paymentMethodInfo }));
      saveSubject.complete();

      // THEN
      expect(paymentMethodInfoService.create).toHaveBeenCalledWith(paymentMethodInfo);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<PaymentMethodInfo>>();
      const paymentMethodInfo = { id: 123 };
      jest.spyOn(paymentMethodInfoService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ paymentMethodInfo });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(paymentMethodInfoService.update).toHaveBeenCalledWith(paymentMethodInfo);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Tracking relationships identifiers', () => {
    describe('trackCurrencysById', () => {
      it('Should return tracked Currencys primary key', () => {
        const entity = { id: 123 };
        const trackResult = comp.trackCurrencysById(0, entity);
        expect(trackResult).toEqual(entity.id);
      });
    });

    describe('trackIssuerById', () => {
      it('Should return tracked Issuer primary key', () => {
        const entity = { id: 'ABC' };
        const trackResult = comp.trackIssuerById(0, entity);
        expect(trackResult).toEqual(entity.id);
      });
    });

    describe('trackCardTokenDataById', () => {
      it('Should return tracked CardTokenData primary key', () => {
        const entity = { id: 123 };
        const trackResult = comp.trackCardTokenDataById(0, entity);
        expect(trackResult).toEqual(entity.id);
      });
    });
  });
});
