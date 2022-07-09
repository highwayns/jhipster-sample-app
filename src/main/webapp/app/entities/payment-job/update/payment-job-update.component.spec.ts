import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { PaymentJobService } from '../service/payment-job.service';
import { IPaymentJob, PaymentJob } from '../payment-job.model';
import { IOrder } from 'app/entities/order/order.model';
import { OrderService } from 'app/entities/order/service/order.service';
import { IPaymentJobAttributes } from 'app/entities/payment-job-attributes/payment-job-attributes.model';
import { PaymentJobAttributesService } from 'app/entities/payment-job-attributes/service/payment-job-attributes.service';
import { IRecurrenceCriteria } from 'app/entities/recurrence-criteria/recurrence-criteria.model';
import { RecurrenceCriteriaService } from 'app/entities/recurrence-criteria/service/recurrence-criteria.service';

import { PaymentJobUpdateComponent } from './payment-job-update.component';

describe('PaymentJob Management Update Component', () => {
  let comp: PaymentJobUpdateComponent;
  let fixture: ComponentFixture<PaymentJobUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let paymentJobService: PaymentJobService;
  let orderService: OrderService;
  let paymentJobAttributesService: PaymentJobAttributesService;
  let recurrenceCriteriaService: RecurrenceCriteriaService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [PaymentJobUpdateComponent],
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
      .overrideTemplate(PaymentJobUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(PaymentJobUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    paymentJobService = TestBed.inject(PaymentJobService);
    orderService = TestBed.inject(OrderService);
    paymentJobAttributesService = TestBed.inject(PaymentJobAttributesService);
    recurrenceCriteriaService = TestBed.inject(RecurrenceCriteriaService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call order query and add missing value', () => {
      const paymentJob: IPaymentJob = { id: 456 };
      const order: IOrder = { id: 542 };
      paymentJob.order = order;

      const orderCollection: IOrder[] = [{ id: 85112 }];
      jest.spyOn(orderService, 'query').mockReturnValue(of(new HttpResponse({ body: orderCollection })));
      const expectedCollection: IOrder[] = [order, ...orderCollection];
      jest.spyOn(orderService, 'addOrderToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ paymentJob });
      comp.ngOnInit();

      expect(orderService.query).toHaveBeenCalled();
      expect(orderService.addOrderToCollectionIfMissing).toHaveBeenCalledWith(orderCollection, order);
      expect(comp.ordersCollection).toEqual(expectedCollection);
    });

    it('Should call attributes query and add missing value', () => {
      const paymentJob: IPaymentJob = { id: 456 };
      const attributes: IPaymentJobAttributes = { id: 20361 };
      paymentJob.attributes = attributes;

      const attributesCollection: IPaymentJobAttributes[] = [{ id: 36809 }];
      jest.spyOn(paymentJobAttributesService, 'query').mockReturnValue(of(new HttpResponse({ body: attributesCollection })));
      const expectedCollection: IPaymentJobAttributes[] = [attributes, ...attributesCollection];
      jest.spyOn(paymentJobAttributesService, 'addPaymentJobAttributesToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ paymentJob });
      comp.ngOnInit();

      expect(paymentJobAttributesService.query).toHaveBeenCalled();
      expect(paymentJobAttributesService.addPaymentJobAttributesToCollectionIfMissing).toHaveBeenCalledWith(
        attributesCollection,
        attributes
      );
      expect(comp.attributesCollection).toEqual(expectedCollection);
    });

    it('Should call recurrenceCriteria query and add missing value', () => {
      const paymentJob: IPaymentJob = { id: 456 };
      const recurrenceCriteria: IRecurrenceCriteria = { id: 79847 };
      paymentJob.recurrenceCriteria = recurrenceCriteria;

      const recurrenceCriteriaCollection: IRecurrenceCriteria[] = [{ id: 32255 }];
      jest.spyOn(recurrenceCriteriaService, 'query').mockReturnValue(of(new HttpResponse({ body: recurrenceCriteriaCollection })));
      const expectedCollection: IRecurrenceCriteria[] = [recurrenceCriteria, ...recurrenceCriteriaCollection];
      jest.spyOn(recurrenceCriteriaService, 'addRecurrenceCriteriaToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ paymentJob });
      comp.ngOnInit();

      expect(recurrenceCriteriaService.query).toHaveBeenCalled();
      expect(recurrenceCriteriaService.addRecurrenceCriteriaToCollectionIfMissing).toHaveBeenCalledWith(
        recurrenceCriteriaCollection,
        recurrenceCriteria
      );
      expect(comp.recurrenceCriteriaCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const paymentJob: IPaymentJob = { id: 456 };
      const order: IOrder = { id: 27677 };
      paymentJob.order = order;
      const attributes: IPaymentJobAttributes = { id: 39283 };
      paymentJob.attributes = attributes;
      const recurrenceCriteria: IRecurrenceCriteria = { id: 40311 };
      paymentJob.recurrenceCriteria = recurrenceCriteria;

      activatedRoute.data = of({ paymentJob });
      comp.ngOnInit();

      expect(comp.editForm.value).toEqual(expect.objectContaining(paymentJob));
      expect(comp.ordersCollection).toContain(order);
      expect(comp.attributesCollection).toContain(attributes);
      expect(comp.recurrenceCriteriaCollection).toContain(recurrenceCriteria);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<PaymentJob>>();
      const paymentJob = { id: 123 };
      jest.spyOn(paymentJobService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ paymentJob });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: paymentJob }));
      saveSubject.complete();

      // THEN
      expect(comp.previousState).toHaveBeenCalled();
      expect(paymentJobService.update).toHaveBeenCalledWith(paymentJob);
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<PaymentJob>>();
      const paymentJob = new PaymentJob();
      jest.spyOn(paymentJobService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ paymentJob });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: paymentJob }));
      saveSubject.complete();

      // THEN
      expect(paymentJobService.create).toHaveBeenCalledWith(paymentJob);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<PaymentJob>>();
      const paymentJob = { id: 123 };
      jest.spyOn(paymentJobService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ paymentJob });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(paymentJobService.update).toHaveBeenCalledWith(paymentJob);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Tracking relationships identifiers', () => {
    describe('trackOrderById', () => {
      it('Should return tracked Order primary key', () => {
        const entity = { id: 123 };
        const trackResult = comp.trackOrderById(0, entity);
        expect(trackResult).toEqual(entity.id);
      });
    });

    describe('trackPaymentJobAttributesById', () => {
      it('Should return tracked PaymentJobAttributes primary key', () => {
        const entity = { id: 123 };
        const trackResult = comp.trackPaymentJobAttributesById(0, entity);
        expect(trackResult).toEqual(entity.id);
      });
    });

    describe('trackRecurrenceCriteriaById', () => {
      it('Should return tracked RecurrenceCriteria primary key', () => {
        const entity = { id: 123 };
        const trackResult = comp.trackRecurrenceCriteriaById(0, entity);
        expect(trackResult).toEqual(entity.id);
      });
    });
  });
});
