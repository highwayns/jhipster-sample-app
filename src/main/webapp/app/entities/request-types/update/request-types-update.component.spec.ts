import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { RequestTypesService } from '../service/request-types.service';
import { IRequestTypes, RequestTypes } from '../request-types.model';

import { RequestTypesUpdateComponent } from './request-types-update.component';

describe('RequestTypes Management Update Component', () => {
  let comp: RequestTypesUpdateComponent;
  let fixture: ComponentFixture<RequestTypesUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let requestTypesService: RequestTypesService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [RequestTypesUpdateComponent],
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
      .overrideTemplate(RequestTypesUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(RequestTypesUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    requestTypesService = TestBed.inject(RequestTypesService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const requestTypes: IRequestTypes = { id: 456 };

      activatedRoute.data = of({ requestTypes });
      comp.ngOnInit();

      expect(comp.editForm.value).toEqual(expect.objectContaining(requestTypes));
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<RequestTypes>>();
      const requestTypes = { id: 123 };
      jest.spyOn(requestTypesService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ requestTypes });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: requestTypes }));
      saveSubject.complete();

      // THEN
      expect(comp.previousState).toHaveBeenCalled();
      expect(requestTypesService.update).toHaveBeenCalledWith(requestTypes);
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<RequestTypes>>();
      const requestTypes = new RequestTypes();
      jest.spyOn(requestTypesService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ requestTypes });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: requestTypes }));
      saveSubject.complete();

      // THEN
      expect(requestTypesService.create).toHaveBeenCalledWith(requestTypes);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<RequestTypes>>();
      const requestTypes = { id: 123 };
      jest.spyOn(requestTypesService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ requestTypes });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(requestTypesService.update).toHaveBeenCalledWith(requestTypes);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
