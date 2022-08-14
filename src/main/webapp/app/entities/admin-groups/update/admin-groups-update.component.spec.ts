import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { AdminGroupsService } from '../service/admin-groups.service';
import { IAdminGroups, AdminGroups } from '../admin-groups.model';

import { AdminGroupsUpdateComponent } from './admin-groups-update.component';

describe('AdminGroups Management Update Component', () => {
  let comp: AdminGroupsUpdateComponent;
  let fixture: ComponentFixture<AdminGroupsUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let adminGroupsService: AdminGroupsService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [AdminGroupsUpdateComponent],
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
      .overrideTemplate(AdminGroupsUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(AdminGroupsUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    adminGroupsService = TestBed.inject(AdminGroupsService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const adminGroups: IAdminGroups = { id: 456 };

      activatedRoute.data = of({ adminGroups });
      comp.ngOnInit();

      expect(comp.editForm.value).toEqual(expect.objectContaining(adminGroups));
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<AdminGroups>>();
      const adminGroups = { id: 123 };
      jest.spyOn(adminGroupsService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ adminGroups });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: adminGroups }));
      saveSubject.complete();

      // THEN
      expect(comp.previousState).toHaveBeenCalled();
      expect(adminGroupsService.update).toHaveBeenCalledWith(adminGroups);
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<AdminGroups>>();
      const adminGroups = new AdminGroups();
      jest.spyOn(adminGroupsService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ adminGroups });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: adminGroups }));
      saveSubject.complete();

      // THEN
      expect(adminGroupsService.create).toHaveBeenCalledWith(adminGroups);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<AdminGroups>>();
      const adminGroups = { id: 123 };
      jest.spyOn(adminGroupsService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ adminGroups });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(adminGroupsService.update).toHaveBeenCalledWith(adminGroups);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
