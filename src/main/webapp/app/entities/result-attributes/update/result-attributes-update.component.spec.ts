import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { ResultAttributesService } from '../service/result-attributes.service';
import { IResultAttributes, ResultAttributes } from '../result-attributes.model';

import { ResultAttributesUpdateComponent } from './result-attributes-update.component';

describe('ResultAttributes Management Update Component', () => {
  let comp: ResultAttributesUpdateComponent;
  let fixture: ComponentFixture<ResultAttributesUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let resultAttributesService: ResultAttributesService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [ResultAttributesUpdateComponent],
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
      .overrideTemplate(ResultAttributesUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(ResultAttributesUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    resultAttributesService = TestBed.inject(ResultAttributesService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const resultAttributes: IResultAttributes = { id: 456 };

      activatedRoute.data = of({ resultAttributes });
      comp.ngOnInit();

      expect(comp.editForm.value).toEqual(expect.objectContaining(resultAttributes));
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ResultAttributes>>();
      const resultAttributes = { id: 123 };
      jest.spyOn(resultAttributesService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ resultAttributes });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: resultAttributes }));
      saveSubject.complete();

      // THEN
      expect(comp.previousState).toHaveBeenCalled();
      expect(resultAttributesService.update).toHaveBeenCalledWith(resultAttributes);
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ResultAttributes>>();
      const resultAttributes = new ResultAttributes();
      jest.spyOn(resultAttributesService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ resultAttributes });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: resultAttributes }));
      saveSubject.complete();

      // THEN
      expect(resultAttributesService.create).toHaveBeenCalledWith(resultAttributes);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ResultAttributes>>();
      const resultAttributes = { id: 123 };
      jest.spyOn(resultAttributesService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ resultAttributes });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(resultAttributesService.update).toHaveBeenCalledWith(resultAttributes);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
