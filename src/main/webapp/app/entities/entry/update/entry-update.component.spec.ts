import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { EntryService } from '../service/entry.service';
import { IEntry, Entry } from '../entry.model';
import { IParameters } from 'app/entities/parameters/parameters.model';
import { ParametersService } from 'app/entities/parameters/service/parameters.service';

import { EntryUpdateComponent } from './entry-update.component';

describe('Entry Management Update Component', () => {
  let comp: EntryUpdateComponent;
  let fixture: ComponentFixture<EntryUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let entryService: EntryService;
  let parametersService: ParametersService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [EntryUpdateComponent],
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
      .overrideTemplate(EntryUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(EntryUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    entryService = TestBed.inject(EntryService);
    parametersService = TestBed.inject(ParametersService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call Parameters query and add missing value', () => {
      const entry: IEntry = { id: 456 };
      const parameters: IParameters = { id: 74303 };
      entry.parameters = parameters;

      const parametersCollection: IParameters[] = [{ id: 36272 }];
      jest.spyOn(parametersService, 'query').mockReturnValue(of(new HttpResponse({ body: parametersCollection })));
      const additionalParameters = [parameters];
      const expectedCollection: IParameters[] = [...additionalParameters, ...parametersCollection];
      jest.spyOn(parametersService, 'addParametersToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ entry });
      comp.ngOnInit();

      expect(parametersService.query).toHaveBeenCalled();
      expect(parametersService.addParametersToCollectionIfMissing).toHaveBeenCalledWith(parametersCollection, ...additionalParameters);
      expect(comp.parametersSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const entry: IEntry = { id: 456 };
      const parameters: IParameters = { id: 90791 };
      entry.parameters = parameters;

      activatedRoute.data = of({ entry });
      comp.ngOnInit();

      expect(comp.editForm.value).toEqual(expect.objectContaining(entry));
      expect(comp.parametersSharedCollection).toContain(parameters);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Entry>>();
      const entry = { id: 123 };
      jest.spyOn(entryService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ entry });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: entry }));
      saveSubject.complete();

      // THEN
      expect(comp.previousState).toHaveBeenCalled();
      expect(entryService.update).toHaveBeenCalledWith(entry);
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Entry>>();
      const entry = new Entry();
      jest.spyOn(entryService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ entry });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: entry }));
      saveSubject.complete();

      // THEN
      expect(entryService.create).toHaveBeenCalledWith(entry);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Entry>>();
      const entry = { id: 123 };
      jest.spyOn(entryService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ entry });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(entryService.update).toHaveBeenCalledWith(entry);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Tracking relationships identifiers', () => {
    describe('trackParametersById', () => {
      it('Should return tracked Parameters primary key', () => {
        const entity = { id: 123 };
        const trackResult = comp.trackParametersById(0, entity);
        expect(trackResult).toEqual(entity.id);
      });
    });
  });
});
