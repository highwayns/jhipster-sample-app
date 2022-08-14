import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { AdminTabsService } from '../service/admin-tabs.service';
import { IAdminTabs, AdminTabs } from '../admin-tabs.model';

import { AdminTabsUpdateComponent } from './admin-tabs-update.component';

describe('AdminTabs Management Update Component', () => {
  let comp: AdminTabsUpdateComponent;
  let fixture: ComponentFixture<AdminTabsUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let adminTabsService: AdminTabsService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [AdminTabsUpdateComponent],
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
      .overrideTemplate(AdminTabsUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(AdminTabsUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    adminTabsService = TestBed.inject(AdminTabsService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const adminTabs: IAdminTabs = { id: 456 };

      activatedRoute.data = of({ adminTabs });
      comp.ngOnInit();

      expect(comp.editForm.value).toEqual(expect.objectContaining(adminTabs));
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<AdminTabs>>();
      const adminTabs = { id: 123 };
      jest.spyOn(adminTabsService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ adminTabs });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: adminTabs }));
      saveSubject.complete();

      // THEN
      expect(comp.previousState).toHaveBeenCalled();
      expect(adminTabsService.update).toHaveBeenCalledWith(adminTabs);
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<AdminTabs>>();
      const adminTabs = new AdminTabs();
      jest.spyOn(adminTabsService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ adminTabs });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: adminTabs }));
      saveSubject.complete();

      // THEN
      expect(adminTabsService.create).toHaveBeenCalledWith(adminTabs);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<AdminTabs>>();
      const adminTabs = { id: 123 };
      jest.spyOn(adminTabsService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ adminTabs });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(adminTabsService.update).toHaveBeenCalledWith(adminTabs);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
