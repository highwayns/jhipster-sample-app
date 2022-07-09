import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { PaymentAttributesService } from '../service/payment-attributes.service';
import { IPaymentAttributes, PaymentAttributes } from '../payment-attributes.model';

import { PaymentAttributesUpdateComponent } from './payment-attributes-update.component';

describe('PaymentAttributes Management Update Component', () => {
  let comp: PaymentAttributesUpdateComponent;
  let fixture: ComponentFixture<PaymentAttributesUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let paymentAttributesService: PaymentAttributesService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [PaymentAttributesUpdateComponent],
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
      .overrideTemplate(PaymentAttributesUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(PaymentAttributesUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    paymentAttributesService = TestBed.inject(PaymentAttributesService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const paymentAttributes: IPaymentAttributes = { id: 456 };

      activatedRoute.data = of({ paymentAttributes });
      comp.ngOnInit();

      expect(comp.editForm.value).toEqual(expect.objectContaining(paymentAttributes));
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<PaymentAttributes>>();
      const paymentAttributes = { id: 123 };
      jest.spyOn(paymentAttributesService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ paymentAttributes });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: paymentAttributes }));
      saveSubject.complete();

      // THEN
      expect(comp.previousState).toHaveBeenCalled();
      expect(paymentAttributesService.update).toHaveBeenCalledWith(paymentAttributes);
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<PaymentAttributes>>();
      const paymentAttributes = new PaymentAttributes();
      jest.spyOn(paymentAttributesService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ paymentAttributes });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: paymentAttributes }));
      saveSubject.complete();

      // THEN
      expect(paymentAttributesService.create).toHaveBeenCalledWith(paymentAttributes);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<PaymentAttributes>>();
      const paymentAttributes = { id: 123 };
      jest.spyOn(paymentAttributesService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ paymentAttributes });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(paymentAttributesService.update).toHaveBeenCalledWith(paymentAttributes);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
