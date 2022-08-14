import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { RequestDescriptionsService } from '../service/request-descriptions.service';
import { IRequestDescriptions, RequestDescriptions } from '../request-descriptions.model';

import { RequestDescriptionsUpdateComponent } from './request-descriptions-update.component';

describe('RequestDescriptions Management Update Component', () => {
  let comp: RequestDescriptionsUpdateComponent;
  let fixture: ComponentFixture<RequestDescriptionsUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let requestDescriptionsService: RequestDescriptionsService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [RequestDescriptionsUpdateComponent],
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
      .overrideTemplate(RequestDescriptionsUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(RequestDescriptionsUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    requestDescriptionsService = TestBed.inject(RequestDescriptionsService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const requestDescriptions: IRequestDescriptions = { id: 456 };

      activatedRoute.data = of({ requestDescriptions });
      comp.ngOnInit();

      expect(comp.editForm.value).toEqual(expect.objectContaining(requestDescriptions));
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<RequestDescriptions>>();
      const requestDescriptions = { id: 123 };
      jest.spyOn(requestDescriptionsService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ requestDescriptions });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: requestDescriptions }));
      saveSubject.complete();

      // THEN
      expect(comp.previousState).toHaveBeenCalled();
      expect(requestDescriptionsService.update).toHaveBeenCalledWith(requestDescriptions);
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<RequestDescriptions>>();
      const requestDescriptions = new RequestDescriptions();
      jest.spyOn(requestDescriptionsService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ requestDescriptions });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: requestDescriptions }));
      saveSubject.complete();

      // THEN
      expect(requestDescriptionsService.create).toHaveBeenCalledWith(requestDescriptions);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<RequestDescriptions>>();
      const requestDescriptions = { id: 123 };
      jest.spyOn(requestDescriptionsService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ requestDescriptions });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(requestDescriptionsService.update).toHaveBeenCalledWith(requestDescriptions);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
