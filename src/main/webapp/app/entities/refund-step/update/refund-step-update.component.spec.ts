import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { RefundStepService } from '../service/refund-step.service';
import { IRefundStep, RefundStep } from '../refund-step.model';
import { IResultAttributes } from 'app/entities/result-attributes/result-attributes.model';
import { ResultAttributesService } from 'app/entities/result-attributes/service/result-attributes.service';

import { RefundStepUpdateComponent } from './refund-step-update.component';

describe('RefundStep Management Update Component', () => {
  let comp: RefundStepUpdateComponent;
  let fixture: ComponentFixture<RefundStepUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let refundStepService: RefundStepService;
  let resultAttributesService: ResultAttributesService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [RefundStepUpdateComponent],
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
      .overrideTemplate(RefundStepUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(RefundStepUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    refundStepService = TestBed.inject(RefundStepService);
    resultAttributesService = TestBed.inject(ResultAttributesService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call resultAttributes query and add missing value', () => {
      const refundStep: IRefundStep = { id: 456 };
      const resultAttributes: IResultAttributes = { id: 66577 };
      refundStep.resultAttributes = resultAttributes;

      const resultAttributesCollection: IResultAttributes[] = [{ id: 26462 }];
      jest.spyOn(resultAttributesService, 'query').mockReturnValue(of(new HttpResponse({ body: resultAttributesCollection })));
      const expectedCollection: IResultAttributes[] = [resultAttributes, ...resultAttributesCollection];
      jest.spyOn(resultAttributesService, 'addResultAttributesToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ refundStep });
      comp.ngOnInit();

      expect(resultAttributesService.query).toHaveBeenCalled();
      expect(resultAttributesService.addResultAttributesToCollectionIfMissing).toHaveBeenCalledWith(
        resultAttributesCollection,
        resultAttributes
      );
      expect(comp.resultAttributesCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const refundStep: IRefundStep = { id: 456 };
      const resultAttributes: IResultAttributes = { id: 58954 };
      refundStep.resultAttributes = resultAttributes;

      activatedRoute.data = of({ refundStep });
      comp.ngOnInit();

      expect(comp.editForm.value).toEqual(expect.objectContaining(refundStep));
      expect(comp.resultAttributesCollection).toContain(resultAttributes);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<RefundStep>>();
      const refundStep = { id: 123 };
      jest.spyOn(refundStepService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ refundStep });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: refundStep }));
      saveSubject.complete();

      // THEN
      expect(comp.previousState).toHaveBeenCalled();
      expect(refundStepService.update).toHaveBeenCalledWith(refundStep);
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<RefundStep>>();
      const refundStep = new RefundStep();
      jest.spyOn(refundStepService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ refundStep });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: refundStep }));
      saveSubject.complete();

      // THEN
      expect(refundStepService.create).toHaveBeenCalledWith(refundStep);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<RefundStep>>();
      const refundStep = { id: 123 };
      jest.spyOn(refundStepService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ refundStep });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(refundStepService.update).toHaveBeenCalledWith(refundStep);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Tracking relationships identifiers', () => {
    describe('trackResultAttributesById', () => {
      it('Should return tracked ResultAttributes primary key', () => {
        const entity = { id: 123 };
        const trackResult = comp.trackResultAttributesById(0, entity);
        expect(trackResult).toEqual(entity.id);
      });
    });
  });
});
