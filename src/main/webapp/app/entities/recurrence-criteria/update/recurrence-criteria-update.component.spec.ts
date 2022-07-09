import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { RecurrenceCriteriaService } from '../service/recurrence-criteria.service';
import { IRecurrenceCriteria, RecurrenceCriteria } from '../recurrence-criteria.model';

import { RecurrenceCriteriaUpdateComponent } from './recurrence-criteria-update.component';

describe('RecurrenceCriteria Management Update Component', () => {
  let comp: RecurrenceCriteriaUpdateComponent;
  let fixture: ComponentFixture<RecurrenceCriteriaUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let recurrenceCriteriaService: RecurrenceCriteriaService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [RecurrenceCriteriaUpdateComponent],
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
      .overrideTemplate(RecurrenceCriteriaUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(RecurrenceCriteriaUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    recurrenceCriteriaService = TestBed.inject(RecurrenceCriteriaService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const recurrenceCriteria: IRecurrenceCriteria = { id: 456 };

      activatedRoute.data = of({ recurrenceCriteria });
      comp.ngOnInit();

      expect(comp.editForm.value).toEqual(expect.objectContaining(recurrenceCriteria));
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<RecurrenceCriteria>>();
      const recurrenceCriteria = { id: 123 };
      jest.spyOn(recurrenceCriteriaService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ recurrenceCriteria });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: recurrenceCriteria }));
      saveSubject.complete();

      // THEN
      expect(comp.previousState).toHaveBeenCalled();
      expect(recurrenceCriteriaService.update).toHaveBeenCalledWith(recurrenceCriteria);
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<RecurrenceCriteria>>();
      const recurrenceCriteria = new RecurrenceCriteria();
      jest.spyOn(recurrenceCriteriaService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ recurrenceCriteria });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: recurrenceCriteria }));
      saveSubject.complete();

      // THEN
      expect(recurrenceCriteriaService.create).toHaveBeenCalledWith(recurrenceCriteria);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<RecurrenceCriteria>>();
      const recurrenceCriteria = { id: 123 };
      jest.spyOn(recurrenceCriteriaService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ recurrenceCriteria });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(recurrenceCriteriaService.update).toHaveBeenCalledWith(recurrenceCriteria);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
