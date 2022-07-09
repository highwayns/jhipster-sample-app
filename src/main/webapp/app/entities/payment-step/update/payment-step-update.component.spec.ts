import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { PaymentStepService } from '../service/payment-step.service';
import { IPaymentStep, PaymentStep } from '../payment-step.model';

import { PaymentStepUpdateComponent } from './payment-step-update.component';

describe('PaymentStep Management Update Component', () => {
  let comp: PaymentStepUpdateComponent;
  let fixture: ComponentFixture<PaymentStepUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let paymentStepService: PaymentStepService;

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

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const paymentStep: IPaymentStep = { id: 456 };

      activatedRoute.data = of({ paymentStep });
      comp.ngOnInit();

      expect(comp.editForm.value).toEqual(expect.objectContaining(paymentStep));
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
});
