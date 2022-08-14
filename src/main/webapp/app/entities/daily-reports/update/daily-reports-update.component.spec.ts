import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { DailyReportsService } from '../service/daily-reports.service';
import { IDailyReports, DailyReports } from '../daily-reports.model';

import { DailyReportsUpdateComponent } from './daily-reports-update.component';

describe('DailyReports Management Update Component', () => {
  let comp: DailyReportsUpdateComponent;
  let fixture: ComponentFixture<DailyReportsUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let dailyReportsService: DailyReportsService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [DailyReportsUpdateComponent],
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
      .overrideTemplate(DailyReportsUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(DailyReportsUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    dailyReportsService = TestBed.inject(DailyReportsService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const dailyReports: IDailyReports = { id: 456 };

      activatedRoute.data = of({ dailyReports });
      comp.ngOnInit();

      expect(comp.editForm.value).toEqual(expect.objectContaining(dailyReports));
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<DailyReports>>();
      const dailyReports = { id: 123 };
      jest.spyOn(dailyReportsService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ dailyReports });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: dailyReports }));
      saveSubject.complete();

      // THEN
      expect(comp.previousState).toHaveBeenCalled();
      expect(dailyReportsService.update).toHaveBeenCalledWith(dailyReports);
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<DailyReports>>();
      const dailyReports = new DailyReports();
      jest.spyOn(dailyReportsService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ dailyReports });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: dailyReports }));
      saveSubject.complete();

      // THEN
      expect(dailyReportsService.create).toHaveBeenCalledWith(dailyReports);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<DailyReports>>();
      const dailyReports = { id: 123 };
      jest.spyOn(dailyReportsService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ dailyReports });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(dailyReportsService.update).toHaveBeenCalledWith(dailyReports);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
