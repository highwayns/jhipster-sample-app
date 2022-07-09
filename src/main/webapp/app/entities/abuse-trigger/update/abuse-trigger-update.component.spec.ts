import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { AbuseTriggerService } from '../service/abuse-trigger.service';
import { IAbuseTrigger, AbuseTrigger } from '../abuse-trigger.model';
import { IParameters } from 'app/entities/parameters/parameters.model';
import { ParametersService } from 'app/entities/parameters/service/parameters.service';

import { AbuseTriggerUpdateComponent } from './abuse-trigger-update.component';

describe('AbuseTrigger Management Update Component', () => {
  let comp: AbuseTriggerUpdateComponent;
  let fixture: ComponentFixture<AbuseTriggerUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let abuseTriggerService: AbuseTriggerService;
  let parametersService: ParametersService;

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
    parametersService = TestBed.inject(ParametersService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call Parameters query and add missing value', () => {
      const abuseTrigger: IAbuseTrigger = { id: 456 };
      const parameters: IParameters = { id: 98668 };
      abuseTrigger.parameters = parameters;

      const parametersCollection: IParameters[] = [{ id: 48332 }];
      jest.spyOn(parametersService, 'query').mockReturnValue(of(new HttpResponse({ body: parametersCollection })));
      const additionalParameters = [parameters];
      const expectedCollection: IParameters[] = [...additionalParameters, ...parametersCollection];
      jest.spyOn(parametersService, 'addParametersToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ abuseTrigger });
      comp.ngOnInit();

      expect(parametersService.query).toHaveBeenCalled();
      expect(parametersService.addParametersToCollectionIfMissing).toHaveBeenCalledWith(parametersCollection, ...additionalParameters);
      expect(comp.parametersSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const abuseTrigger: IAbuseTrigger = { id: 456 };
      const parameters: IParameters = { id: 61625 };
      abuseTrigger.parameters = parameters;

      activatedRoute.data = of({ abuseTrigger });
      comp.ngOnInit();

      expect(comp.editForm.value).toEqual(expect.objectContaining(abuseTrigger));
      expect(comp.parametersSharedCollection).toContain(parameters);
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
