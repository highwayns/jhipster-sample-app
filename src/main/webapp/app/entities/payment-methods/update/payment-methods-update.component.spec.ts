import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { PaymentMethodsService } from '../service/payment-methods.service';
import { IPaymentMethods, PaymentMethods } from '../payment-methods.model';

import { PaymentMethodsUpdateComponent } from './payment-methods-update.component';

describe('PaymentMethods Management Update Component', () => {
  let comp: PaymentMethodsUpdateComponent;
  let fixture: ComponentFixture<PaymentMethodsUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let paymentMethodsService: PaymentMethodsService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [PaymentMethodsUpdateComponent],
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
      .overrideTemplate(PaymentMethodsUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(PaymentMethodsUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    paymentMethodsService = TestBed.inject(PaymentMethodsService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const paymentMethods: IPaymentMethods = { id: 456 };

      activatedRoute.data = of({ paymentMethods });
      comp.ngOnInit();

      expect(comp.editForm.value).toEqual(expect.objectContaining(paymentMethods));
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<PaymentMethods>>();
      const paymentMethods = { id: 123 };
      jest.spyOn(paymentMethodsService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ paymentMethods });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: paymentMethods }));
      saveSubject.complete();

      // THEN
      expect(comp.previousState).toHaveBeenCalled();
      expect(paymentMethodsService.update).toHaveBeenCalledWith(paymentMethods);
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<PaymentMethods>>();
      const paymentMethods = new PaymentMethods();
      jest.spyOn(paymentMethodsService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ paymentMethods });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: paymentMethods }));
      saveSubject.complete();

      // THEN
      expect(paymentMethodsService.create).toHaveBeenCalledWith(paymentMethods);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<PaymentMethods>>();
      const paymentMethods = { id: 123 };
      jest.spyOn(paymentMethodsService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ paymentMethods });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(paymentMethodsService.update).toHaveBeenCalledWith(paymentMethods);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
