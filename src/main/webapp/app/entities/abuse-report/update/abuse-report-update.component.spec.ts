import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { AbuseReportService } from '../service/abuse-report.service';
import { IAbuseReport, AbuseReport } from '../abuse-report.model';

import { AbuseReportUpdateComponent } from './abuse-report-update.component';

describe('AbuseReport Management Update Component', () => {
  let comp: AbuseReportUpdateComponent;
  let fixture: ComponentFixture<AbuseReportUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let abuseReportService: AbuseReportService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [AbuseReportUpdateComponent],
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
      .overrideTemplate(AbuseReportUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(AbuseReportUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    abuseReportService = TestBed.inject(AbuseReportService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const abuseReport: IAbuseReport = { id: 456 };

      activatedRoute.data = of({ abuseReport });
      comp.ngOnInit();

      expect(comp.editForm.value).toEqual(expect.objectContaining(abuseReport));
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<AbuseReport>>();
      const abuseReport = { id: 123 };
      jest.spyOn(abuseReportService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ abuseReport });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: abuseReport }));
      saveSubject.complete();

      // THEN
      expect(comp.previousState).toHaveBeenCalled();
      expect(abuseReportService.update).toHaveBeenCalledWith(abuseReport);
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<AbuseReport>>();
      const abuseReport = new AbuseReport();
      jest.spyOn(abuseReportService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ abuseReport });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: abuseReport }));
      saveSubject.complete();

      // THEN
      expect(abuseReportService.create).toHaveBeenCalledWith(abuseReport);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<AbuseReport>>();
      const abuseReport = { id: 123 };
      jest.spyOn(abuseReportService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ abuseReport });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(abuseReportService.update).toHaveBeenCalledWith(abuseReport);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
