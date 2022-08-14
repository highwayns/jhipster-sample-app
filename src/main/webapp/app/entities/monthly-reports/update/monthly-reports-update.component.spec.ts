import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { MonthlyReportsService } from '../service/monthly-reports.service';
import { IMonthlyReports, MonthlyReports } from '../monthly-reports.model';

import { MonthlyReportsUpdateComponent } from './monthly-reports-update.component';

describe('MonthlyReports Management Update Component', () => {
  let comp: MonthlyReportsUpdateComponent;
  let fixture: ComponentFixture<MonthlyReportsUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let monthlyReportsService: MonthlyReportsService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [MonthlyReportsUpdateComponent],
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
      .overrideTemplate(MonthlyReportsUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(MonthlyReportsUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    monthlyReportsService = TestBed.inject(MonthlyReportsService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const monthlyReports: IMonthlyReports = { id: 456 };

      activatedRoute.data = of({ monthlyReports });
      comp.ngOnInit();

      expect(comp.editForm.value).toEqual(expect.objectContaining(monthlyReports));
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<MonthlyReports>>();
      const monthlyReports = { id: 123 };
      jest.spyOn(monthlyReportsService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ monthlyReports });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: monthlyReports }));
      saveSubject.complete();

      // THEN
      expect(comp.previousState).toHaveBeenCalled();
      expect(monthlyReportsService.update).toHaveBeenCalledWith(monthlyReports);
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<MonthlyReports>>();
      const monthlyReports = new MonthlyReports();
      jest.spyOn(monthlyReportsService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ monthlyReports });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: monthlyReports }));
      saveSubject.complete();

      // THEN
      expect(monthlyReportsService.create).toHaveBeenCalledWith(monthlyReports);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<MonthlyReports>>();
      const monthlyReports = { id: 123 };
      jest.spyOn(monthlyReportsService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ monthlyReports });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(monthlyReportsService.update).toHaveBeenCalledWith(monthlyReports);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
