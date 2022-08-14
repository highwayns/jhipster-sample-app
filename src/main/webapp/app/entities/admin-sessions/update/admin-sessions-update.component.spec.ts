import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { AdminSessionsService } from '../service/admin-sessions.service';
import { IAdminSessions, AdminSessions } from '../admin-sessions.model';

import { AdminSessionsUpdateComponent } from './admin-sessions-update.component';

describe('AdminSessions Management Update Component', () => {
  let comp: AdminSessionsUpdateComponent;
  let fixture: ComponentFixture<AdminSessionsUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let adminSessionsService: AdminSessionsService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [AdminSessionsUpdateComponent],
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
      .overrideTemplate(AdminSessionsUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(AdminSessionsUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    adminSessionsService = TestBed.inject(AdminSessionsService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const adminSessions: IAdminSessions = { id: 456 };

      activatedRoute.data = of({ adminSessions });
      comp.ngOnInit();

      expect(comp.editForm.value).toEqual(expect.objectContaining(adminSessions));
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<AdminSessions>>();
      const adminSessions = { id: 123 };
      jest.spyOn(adminSessionsService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ adminSessions });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: adminSessions }));
      saveSubject.complete();

      // THEN
      expect(comp.previousState).toHaveBeenCalled();
      expect(adminSessionsService.update).toHaveBeenCalledWith(adminSessions);
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<AdminSessions>>();
      const adminSessions = new AdminSessions();
      jest.spyOn(adminSessionsService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ adminSessions });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: adminSessions }));
      saveSubject.complete();

      // THEN
      expect(adminSessionsService.create).toHaveBeenCalledWith(adminSessions);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<AdminSessions>>();
      const adminSessions = { id: 123 };
      jest.spyOn(adminSessionsService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ adminSessions });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(adminSessionsService.update).toHaveBeenCalledWith(adminSessions);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
