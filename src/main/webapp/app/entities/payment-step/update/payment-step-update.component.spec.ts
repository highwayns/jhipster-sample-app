import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { PaymentStepService } from '../service/payment-step.service';
import { IPaymentStep, PaymentStep } from '../payment-step.model';
import { IPaymentMethods } from 'app/entities/payment-methods/payment-methods.model';
import { PaymentMethodsService } from 'app/entities/payment-methods/service/payment-methods.service';

import { PaymentStepUpdateComponent } from './payment-step-update.component';

describe('PaymentStep Management Update Component', () => {
  let comp: PaymentStepUpdateComponent;
  let fixture: ComponentFixture<PaymentStepUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let paymentStepService: PaymentStepService;
  let paymentMethodsService: PaymentMethodsService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [PaymentStepUpdateComponent],
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
      .overrideTemplate(PaymentStepUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(PaymentStepUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    paymentStepService = TestBed.inject(PaymentStepService);
    paymentMethodsService = TestBed.inject(PaymentMethodsService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call PaymentMethods query and add missing value', () => {
      const paymentStep: IPaymentStep = { id: 456 };
      const paymentMethods: IPaymentMethods = { id: 89075 };
      paymentStep.paymentMethods = paymentMethods;

      const paymentMethodsCollection: IPaymentMethods[] = [{ id: 58629 }];
      jest.spyOn(paymentMethodsService, 'query').mockReturnValue(of(new HttpResponse({ body: paymentMethodsCollection })));
      const additionalPaymentMethods = [paymentMethods];
      const expectedCollection: IPaymentMethods[] = [...additionalPaymentMethods, ...paymentMethodsCollection];
      jest.spyOn(paymentMethodsService, 'addPaymentMethodsToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ paymentStep });
      comp.ngOnInit();

      expect(paymentMethodsService.query).toHaveBeenCalled();
      expect(paymentMethodsService.addPaymentMethodsToCollectionIfMissing).toHaveBeenCalledWith(
        paymentMethodsCollection,
        ...additionalPaymentMethods
      );
      expect(comp.paymentMethodsSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const paymentStep: IPaymentStep = { id: 456 };
      const paymentMethods: IPaymentMethods = { id: 59855 };
      paymentStep.paymentMethods = paymentMethods;

      activatedRoute.data = of({ paymentStep });
      comp.ngOnInit();

      expect(comp.editForm.value).toEqual(expect.objectContaining(paymentStep));
      expect(comp.paymentMethodsSharedCollection).toContain(paymentMethods);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<PaymentStep>>();
      const paymentStep = { id: 123 };
      jest.spyOn(paymentStepService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ paymentStep });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: paymentStep }));
      saveSubject.complete();

      // THEN
      expect(comp.previousState).toHaveBeenCalled();
      expect(paymentStepService.update).toHaveBeenCalledWith(paymentStep);
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<PaymentStep>>();
      const paymentStep = new PaymentStep();
      jest.spyOn(paymentStepService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ paymentStep });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: paymentStep }));
      saveSubject.complete();

      // THEN
      expect(paymentStepService.create).toHaveBeenCalledWith(paymentStep);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<PaymentStep>>();
      const paymentStep = { id: 123 };
      jest.spyOn(paymentStepService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ paymentStep });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(paymentStepService.update).toHaveBeenCalledWith(paymentStep);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Tracking relationships identifiers', () => {
    describe('trackPaymentMethodsById', () => {
      it('Should return tracked PaymentMethods primary key', () => {
        const entity = { id: 123 };
        const trackResult = comp.trackPaymentMethodsById(0, entity);
        expect(trackResult).toEqual(entity.id);
      });
    });
  });
});
