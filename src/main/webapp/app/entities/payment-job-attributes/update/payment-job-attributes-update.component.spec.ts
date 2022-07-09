import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { PaymentJobAttributesService } from '../service/payment-job-attributes.service';
import { IPaymentJobAttributes, PaymentJobAttributes } from '../payment-job-attributes.model';

import { PaymentJobAttributesUpdateComponent } from './payment-job-attributes-update.component';

describe('PaymentJobAttributes Management Update Component', () => {
  let comp: PaymentJobAttributesUpdateComponent;
  let fixture: ComponentFixture<PaymentJobAttributesUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let paymentJobAttributesService: PaymentJobAttributesService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [PaymentJobAttributesUpdateComponent],
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
      .overrideTemplate(PaymentJobAttributesUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(PaymentJobAttributesUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    paymentJobAttributesService = TestBed.inject(PaymentJobAttributesService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const paymentJobAttributes: IPaymentJobAttributes = { id: 456 };

      activatedRoute.data = of({ paymentJobAttributes });
      comp.ngOnInit();

      expect(comp.editForm.value).toEqual(expect.objectContaining(paymentJobAttributes));
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<PaymentJobAttributes>>();
      const paymentJobAttributes = { id: 123 };
      jest.spyOn(paymentJobAttributesService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ paymentJobAttributes });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: paymentJobAttributes }));
      saveSubject.complete();

      // THEN
      expect(comp.previousState).toHaveBeenCalled();
      expect(paymentJobAttributesService.update).toHaveBeenCalledWith(paymentJobAttributes);
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<PaymentJobAttributes>>();
      const paymentJobAttributes = new PaymentJobAttributes();
      jest.spyOn(paymentJobAttributesService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ paymentJobAttributes });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: paymentJobAttributes }));
      saveSubject.complete();

      // THEN
      expect(paymentJobAttributesService.create).toHaveBeenCalledWith(paymentJobAttributes);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<PaymentJobAttributes>>();
      const paymentJobAttributes = { id: 123 };
      jest.spyOn(paymentJobAttributesService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ paymentJobAttributes });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(paymentJobAttributesService.update).toHaveBeenCalledWith(paymentJobAttributes);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
