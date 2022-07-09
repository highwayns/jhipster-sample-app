import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { PaymentService } from '../service/payment.service';
import { IPayment, Payment } from '../payment.model';
import { IErrorReport } from 'app/entities/error-report/error-report.model';
import { ErrorReportService } from 'app/entities/error-report/service/error-report.service';
import { IAbuseReport } from 'app/entities/abuse-report/abuse-report.model';
import { AbuseReportService } from 'app/entities/abuse-report/service/abuse-report.service';
import { IPaymentAttributes } from 'app/entities/payment-attributes/payment-attributes.model';
import { PaymentAttributesService } from 'app/entities/payment-attributes/service/payment-attributes.service';

import { PaymentUpdateComponent } from './payment-update.component';

describe('Payment Management Update Component', () => {
  let comp: PaymentUpdateComponent;
  let fixture: ComponentFixture<PaymentUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let paymentService: PaymentService;
  let errorReportService: ErrorReportService;
  let abuseReportService: AbuseReportService;
  let paymentAttributesService: PaymentAttributesService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [PaymentUpdateComponent],
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
      .overrideTemplate(PaymentUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(PaymentUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    paymentService = TestBed.inject(PaymentService);
    errorReportService = TestBed.inject(ErrorReportService);
    abuseReportService = TestBed.inject(AbuseReportService);
    paymentAttributesService = TestBed.inject(PaymentAttributesService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call lastErrorReport query and add missing value', () => {
      const payment: IPayment = { id: 456 };
      const lastErrorReport: IErrorReport = { id: 5769 };
      payment.lastErrorReport = lastErrorReport;

      const lastErrorReportCollection: IErrorReport[] = [{ id: 89451 }];
      jest.spyOn(errorReportService, 'query').mockReturnValue(of(new HttpResponse({ body: lastErrorReportCollection })));
      const expectedCollection: IErrorReport[] = [lastErrorReport, ...lastErrorReportCollection];
      jest.spyOn(errorReportService, 'addErrorReportToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ payment });
      comp.ngOnInit();

      expect(errorReportService.query).toHaveBeenCalled();
      expect(errorReportService.addErrorReportToCollectionIfMissing).toHaveBeenCalledWith(lastErrorReportCollection, lastErrorReport);
      expect(comp.lastErrorReportsCollection).toEqual(expectedCollection);
    });

    it('Should call abuseReport query and add missing value', () => {
      const payment: IPayment = { id: 456 };
      const abuseReport: IAbuseReport = { id: 59444 };
      payment.abuseReport = abuseReport;

      const abuseReportCollection: IAbuseReport[] = [{ id: 83531 }];
      jest.spyOn(abuseReportService, 'query').mockReturnValue(of(new HttpResponse({ body: abuseReportCollection })));
      const expectedCollection: IAbuseReport[] = [abuseReport, ...abuseReportCollection];
      jest.spyOn(abuseReportService, 'addAbuseReportToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ payment });
      comp.ngOnInit();

      expect(abuseReportService.query).toHaveBeenCalled();
      expect(abuseReportService.addAbuseReportToCollectionIfMissing).toHaveBeenCalledWith(abuseReportCollection, abuseReport);
      expect(comp.abuseReportsCollection).toEqual(expectedCollection);
    });

    it('Should call attributes query and add missing value', () => {
      const payment: IPayment = { id: 456 };
      const attributes: IPaymentAttributes = { id: 34555 };
      payment.attributes = attributes;

      const attributesCollection: IPaymentAttributes[] = [{ id: 76745 }];
      jest.spyOn(paymentAttributesService, 'query').mockReturnValue(of(new HttpResponse({ body: attributesCollection })));
      const expectedCollection: IPaymentAttributes[] = [attributes, ...attributesCollection];
      jest.spyOn(paymentAttributesService, 'addPaymentAttributesToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ payment });
      comp.ngOnInit();

      expect(paymentAttributesService.query).toHaveBeenCalled();
      expect(paymentAttributesService.addPaymentAttributesToCollectionIfMissing).toHaveBeenCalledWith(attributesCollection, attributes);
      expect(comp.attributesCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const payment: IPayment = { id: 456 };
      const lastErrorReport: IErrorReport = { id: 3983 };
      payment.lastErrorReport = lastErrorReport;
      const abuseReport: IAbuseReport = { id: 23644 };
      payment.abuseReport = abuseReport;
      const attributes: IPaymentAttributes = { id: 11806 };
      payment.attributes = attributes;

      activatedRoute.data = of({ payment });
      comp.ngOnInit();

      expect(comp.editForm.value).toEqual(expect.objectContaining(payment));
      expect(comp.lastErrorReportsCollection).toContain(lastErrorReport);
      expect(comp.abuseReportsCollection).toContain(abuseReport);
      expect(comp.attributesCollection).toContain(attributes);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Payment>>();
      const payment = { id: 123 };
      jest.spyOn(paymentService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ payment });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: payment }));
      saveSubject.complete();

      // THEN
      expect(comp.previousState).toHaveBeenCalled();
      expect(paymentService.update).toHaveBeenCalledWith(payment);
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Payment>>();
      const payment = new Payment();
      jest.spyOn(paymentService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ payment });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: payment }));
      saveSubject.complete();

      // THEN
      expect(paymentService.create).toHaveBeenCalledWith(payment);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Payment>>();
      const payment = { id: 123 };
      jest.spyOn(paymentService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ payment });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(paymentService.update).toHaveBeenCalledWith(payment);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Tracking relationships identifiers', () => {
    describe('trackErrorReportById', () => {
      it('Should return tracked ErrorReport primary key', () => {
        const entity = { id: 123 };
        const trackResult = comp.trackErrorReportById(0, entity);
        expect(trackResult).toEqual(entity.id);
      });
    });

    describe('trackAbuseReportById', () => {
      it('Should return tracked AbuseReport primary key', () => {
        const entity = { id: 123 };
        const trackResult = comp.trackAbuseReportById(0, entity);
        expect(trackResult).toEqual(entity.id);
      });
    });

    describe('trackPaymentAttributesById', () => {
      it('Should return tracked PaymentAttributes primary key', () => {
        const entity = { id: 123 };
        const trackResult = comp.trackPaymentAttributesById(0, entity);
        expect(trackResult).toEqual(entity.id);
      });
    });
  });
});
