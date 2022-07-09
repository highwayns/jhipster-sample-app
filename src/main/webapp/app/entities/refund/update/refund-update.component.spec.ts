import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { RefundService } from '../service/refund.service';
import { IRefund, Refund } from '../refund.model';
import { IRefundStep } from 'app/entities/refund-step/refund-step.model';
import { RefundStepService } from 'app/entities/refund-step/service/refund-step.service';

import { RefundUpdateComponent } from './refund-update.component';

describe('Refund Management Update Component', () => {
  let comp: RefundUpdateComponent;
  let fixture: ComponentFixture<RefundUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let refundService: RefundService;
  let refundStepService: RefundStepService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [RefundUpdateComponent],
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
      .overrideTemplate(RefundUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(RefundUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    refundService = TestBed.inject(RefundService);
    refundStepService = TestBed.inject(RefundStepService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call RefundStep query and add missing value', () => {
      const refund: IRefund = { id: 456 };
      const steps: IRefundStep = { id: 35620 };
      refund.steps = steps;

      const refundStepCollection: IRefundStep[] = [{ id: 57490 }];
      jest.spyOn(refundStepService, 'query').mockReturnValue(of(new HttpResponse({ body: refundStepCollection })));
      const additionalRefundSteps = [steps];
      const expectedCollection: IRefundStep[] = [...additionalRefundSteps, ...refundStepCollection];
      jest.spyOn(refundStepService, 'addRefundStepToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ refund });
      comp.ngOnInit();

      expect(refundStepService.query).toHaveBeenCalled();
      expect(refundStepService.addRefundStepToCollectionIfMissing).toHaveBeenCalledWith(refundStepCollection, ...additionalRefundSteps);
      expect(comp.refundStepsSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const refund: IRefund = { id: 456 };
      const steps: IRefundStep = { id: 35307 };
      refund.steps = steps;

      activatedRoute.data = of({ refund });
      comp.ngOnInit();

      expect(comp.editForm.value).toEqual(expect.objectContaining(refund));
      expect(comp.refundStepsSharedCollection).toContain(steps);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Refund>>();
      const refund = { id: 123 };
      jest.spyOn(refundService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ refund });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: refund }));
      saveSubject.complete();

      // THEN
      expect(comp.previousState).toHaveBeenCalled();
      expect(refundService.update).toHaveBeenCalledWith(refund);
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Refund>>();
      const refund = new Refund();
      jest.spyOn(refundService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ refund });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: refund }));
      saveSubject.complete();

      // THEN
      expect(refundService.create).toHaveBeenCalledWith(refund);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Refund>>();
      const refund = { id: 123 };
      jest.spyOn(refundService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ refund });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(refundService.update).toHaveBeenCalledWith(refund);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Tracking relationships identifiers', () => {
    describe('trackRefundStepById', () => {
      it('Should return tracked RefundStep primary key', () => {
        const entity = { id: 123 };
        const trackResult = comp.trackRefundStepById(0, entity);
        expect(trackResult).toEqual(entity.id);
      });
    });
  });
});
