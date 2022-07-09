import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { ErrorReportService } from '../service/error-report.service';
import { IErrorReport, ErrorReport } from '../error-report.model';
import { IEntry } from 'app/entities/entry/entry.model';
import { EntryService } from 'app/entities/entry/service/entry.service';

import { ErrorReportUpdateComponent } from './error-report-update.component';

describe('ErrorReport Management Update Component', () => {
  let comp: ErrorReportUpdateComponent;
  let fixture: ComponentFixture<ErrorReportUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let errorReportService: ErrorReportService;
  let entryService: EntryService;

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
    entryService = TestBed.inject(EntryService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call Entry query and add missing value', () => {
      const errorReport: IErrorReport = { id: 456 };
      const errors: IEntry = { id: 59865 };
      errorReport.errors = errors;
      const warnings: IEntry = { id: 35533 };
      errorReport.warnings = warnings;

      const entryCollection: IEntry[] = [{ id: 96441 }];
      jest.spyOn(entryService, 'query').mockReturnValue(of(new HttpResponse({ body: entryCollection })));
      const additionalEntries = [errors, warnings];
      const expectedCollection: IEntry[] = [...additionalEntries, ...entryCollection];
      jest.spyOn(entryService, 'addEntryToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ errorReport });
      comp.ngOnInit();

      expect(entryService.query).toHaveBeenCalled();
      expect(entryService.addEntryToCollectionIfMissing).toHaveBeenCalledWith(entryCollection, ...additionalEntries);
      expect(comp.entriesSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const errorReport: IErrorReport = { id: 456 };
      const errors: IEntry = { id: 37116 };
      errorReport.errors = errors;
      const warnings: IEntry = { id: 98099 };
      errorReport.warnings = warnings;

      activatedRoute.data = of({ errorReport });
      comp.ngOnInit();

      expect(comp.editForm.value).toEqual(expect.objectContaining(errorReport));
      expect(comp.entriesSharedCollection).toContain(errors);
      expect(comp.entriesSharedCollection).toContain(warnings);
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

  describe('Tracking relationships identifiers', () => {
    describe('trackEntryById', () => {
      it('Should return tracked Entry primary key', () => {
        const entity = { id: 123 };
        const trackResult = comp.trackEntryById(0, entity);
        expect(trackResult).toEqual(entity.id);
      });
    });
  });
});
