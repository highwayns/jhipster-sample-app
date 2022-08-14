import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { FeeScheduleService } from '../service/fee-schedule.service';
import { IFeeSchedule, FeeSchedule } from '../fee-schedule.model';

import { FeeScheduleUpdateComponent } from './fee-schedule-update.component';

describe('FeeSchedule Management Update Component', () => {
  let comp: FeeScheduleUpdateComponent;
  let fixture: ComponentFixture<FeeScheduleUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let feeScheduleService: FeeScheduleService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [FeeScheduleUpdateComponent],
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
      .overrideTemplate(FeeScheduleUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(FeeScheduleUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    feeScheduleService = TestBed.inject(FeeScheduleService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const feeSchedule: IFeeSchedule = { id: 456 };

      activatedRoute.data = of({ feeSchedule });
      comp.ngOnInit();

      expect(comp.editForm.value).toEqual(expect.objectContaining(feeSchedule));
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<FeeSchedule>>();
      const feeSchedule = { id: 123 };
      jest.spyOn(feeScheduleService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ feeSchedule });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: feeSchedule }));
      saveSubject.complete();

      // THEN
      expect(comp.previousState).toHaveBeenCalled();
      expect(feeScheduleService.update).toHaveBeenCalledWith(feeSchedule);
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<FeeSchedule>>();
      const feeSchedule = new FeeSchedule();
      jest.spyOn(feeScheduleService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ feeSchedule });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: feeSchedule }));
      saveSubject.complete();

      // THEN
      expect(feeScheduleService.create).toHaveBeenCalledWith(feeSchedule);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<FeeSchedule>>();
      const feeSchedule = { id: 123 };
      jest.spyOn(feeScheduleService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ feeSchedule });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(feeScheduleService.update).toHaveBeenCalledWith(feeSchedule);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
