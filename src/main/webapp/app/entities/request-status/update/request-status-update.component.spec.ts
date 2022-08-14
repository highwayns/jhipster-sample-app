import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { RequestStatusService } from '../service/request-status.service';
import { IRequestStatus, RequestStatus } from '../request-status.model';

import { RequestStatusUpdateComponent } from './request-status-update.component';

describe('RequestStatus Management Update Component', () => {
  let comp: RequestStatusUpdateComponent;
  let fixture: ComponentFixture<RequestStatusUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let requestStatusService: RequestStatusService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [RequestStatusUpdateComponent],
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
      .overrideTemplate(RequestStatusUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(RequestStatusUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    requestStatusService = TestBed.inject(RequestStatusService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const requestStatus: IRequestStatus = { id: 456 };

      activatedRoute.data = of({ requestStatus });
      comp.ngOnInit();

      expect(comp.editForm.value).toEqual(expect.objectContaining(requestStatus));
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<RequestStatus>>();
      const requestStatus = { id: 123 };
      jest.spyOn(requestStatusService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ requestStatus });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: requestStatus }));
      saveSubject.complete();

      // THEN
      expect(comp.previousState).toHaveBeenCalled();
      expect(requestStatusService.update).toHaveBeenCalledWith(requestStatus);
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<RequestStatus>>();
      const requestStatus = new RequestStatus();
      jest.spyOn(requestStatusService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ requestStatus });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: requestStatus }));
      saveSubject.complete();

      // THEN
      expect(requestStatusService.create).toHaveBeenCalledWith(requestStatus);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<RequestStatus>>();
      const requestStatus = { id: 123 };
      jest.spyOn(requestStatusService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ requestStatus });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(requestStatusService.update).toHaveBeenCalledWith(requestStatus);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
