import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { PaymentMethodInfoService } from '../service/payment-method-info.service';
import { IPaymentMethodInfo, PaymentMethodInfo } from '../payment-method-info.model';

import { PaymentMethodInfoUpdateComponent } from './payment-method-info-update.component';

describe('PaymentMethodInfo Management Update Component', () => {
  let comp: PaymentMethodInfoUpdateComponent;
  let fixture: ComponentFixture<PaymentMethodInfoUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let paymentMethodInfoService: PaymentMethodInfoService;

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

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const paymentMethodInfo: IPaymentMethodInfo = { id: 456 };

      activatedRoute.data = of({ paymentMethodInfo });
      comp.ngOnInit();

      expect(comp.editForm.value).toEqual(expect.objectContaining(paymentMethodInfo));
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
});
