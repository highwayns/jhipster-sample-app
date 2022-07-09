import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { ErrorReportService } from '../service/error-report.service';
import { IErrorReport, ErrorReport } from '../error-report.model';

import { ErrorReportUpdateComponent } from './error-report-update.component';

describe('ErrorReport Management Update Component', () => {
  let comp: ErrorReportUpdateComponent;
  let fixture: ComponentFixture<ErrorReportUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let errorReportService: ErrorReportService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [ErrorReportUpdateComponent],
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
      .overrideTemplate(ErrorReportUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(ErrorReportUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    errorReportService = TestBed.inject(ErrorReportService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const errorReport: IErrorReport = { id: 456 };

      activatedRoute.data = of({ errorReport });
      comp.ngOnInit();

      expect(comp.editForm.value).toEqual(expect.objectContaining(errorReport));
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ErrorReport>>();
      const errorReport = { id: 123 };
      jest.spyOn(errorReportService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ errorReport });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: errorReport }));
      saveSubject.complete();

      // THEN
      expect(comp.previousState).toHaveBeenCalled();
      expect(errorReportService.update).toHaveBeenCalledWith(errorReport);
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ErrorReport>>();
      const errorReport = new ErrorReport();
      jest.spyOn(errorReportService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ errorReport });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: errorReport }));
      saveSubject.complete();

      // THEN
      expect(errorReportService.create).toHaveBeenCalledWith(errorReport);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ErrorReport>>();
      const errorReport = { id: 123 };
      jest.spyOn(errorReportService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ errorReport });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(errorReportService.update).toHaveBeenCalledWith(errorReport);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
