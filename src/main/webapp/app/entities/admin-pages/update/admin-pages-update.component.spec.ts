import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { AdminPagesService } from '../service/admin-pages.service';
import { IAdminPages, AdminPages } from '../admin-pages.model';

import { AdminPagesUpdateComponent } from './admin-pages-update.component';

describe('AdminPages Management Update Component', () => {
  let comp: AdminPagesUpdateComponent;
  let fixture: ComponentFixture<AdminPagesUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let adminPagesService: AdminPagesService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [AdminPagesUpdateComponent],
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
      .overrideTemplate(AdminPagesUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(AdminPagesUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    adminPagesService = TestBed.inject(AdminPagesService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const adminPages: IAdminPages = { id: 456 };

      activatedRoute.data = of({ adminPages });
      comp.ngOnInit();

      expect(comp.editForm.value).toEqual(expect.objectContaining(adminPages));
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<AdminPages>>();
      const adminPages = { id: 123 };
      jest.spyOn(adminPagesService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ adminPages });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: adminPages }));
      saveSubject.complete();

      // THEN
      expect(comp.previousState).toHaveBeenCalled();
      expect(adminPagesService.update).toHaveBeenCalledWith(adminPages);
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<AdminPages>>();
      const adminPages = new AdminPages();
      jest.spyOn(adminPagesService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ adminPages });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: adminPages }));
      saveSubject.complete();

      // THEN
      expect(adminPagesService.create).toHaveBeenCalledWith(adminPages);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<AdminPages>>();
      const adminPages = { id: 123 };
      jest.spyOn(adminPagesService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ adminPages });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(adminPagesService.update).toHaveBeenCalledWith(adminPages);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
