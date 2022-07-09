import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { AbuseTriggerService } from '../service/abuse-trigger.service';
import { IAbuseTrigger, AbuseTrigger } from '../abuse-trigger.model';

import { AbuseTriggerUpdateComponent } from './abuse-trigger-update.component';

describe('AbuseTrigger Management Update Component', () => {
  let comp: AbuseTriggerUpdateComponent;
  let fixture: ComponentFixture<AbuseTriggerUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let abuseTriggerService: AbuseTriggerService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [AbuseTriggerUpdateComponent],
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
      .overrideTemplate(AbuseTriggerUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(AbuseTriggerUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    abuseTriggerService = TestBed.inject(AbuseTriggerService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const abuseTrigger: IAbuseTrigger = { id: 456 };

      activatedRoute.data = of({ abuseTrigger });
      comp.ngOnInit();

      expect(comp.editForm.value).toEqual(expect.objectContaining(abuseTrigger));
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<AbuseTrigger>>();
      const abuseTrigger = { id: 123 };
      jest.spyOn(abuseTriggerService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ abuseTrigger });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: abuseTrigger }));
      saveSubject.complete();

      // THEN
      expect(comp.previousState).toHaveBeenCalled();
      expect(abuseTriggerService.update).toHaveBeenCalledWith(abuseTrigger);
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<AbuseTrigger>>();
      const abuseTrigger = new AbuseTrigger();
      jest.spyOn(abuseTriggerService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ abuseTrigger });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: abuseTrigger }));
      saveSubject.complete();

      // THEN
      expect(abuseTriggerService.create).toHaveBeenCalledWith(abuseTrigger);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<AbuseTrigger>>();
      const abuseTrigger = { id: 123 };
      jest.spyOn(abuseTriggerService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ abuseTrigger });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(abuseTriggerService.update).toHaveBeenCalledWith(abuseTrigger);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
