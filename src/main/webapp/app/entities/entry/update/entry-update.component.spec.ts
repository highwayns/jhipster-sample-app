import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { EntryService } from '../service/entry.service';
import { IEntry, Entry } from '../entry.model';

import { EntryUpdateComponent } from './entry-update.component';

describe('Entry Management Update Component', () => {
  let comp: EntryUpdateComponent;
  let fixture: ComponentFixture<EntryUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let entryService: EntryService;

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

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const entry: IEntry = { id: 456 };

      activatedRoute.data = of({ entry });
      comp.ngOnInit();

      expect(comp.editForm.value).toEqual(expect.objectContaining(entry));
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
});
