import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { AdminGroupsTabsService } from '../service/admin-groups-tabs.service';
import { IAdminGroupsTabs, AdminGroupsTabs } from '../admin-groups-tabs.model';
import { IAdminTabs } from 'app/entities/admin-tabs/admin-tabs.model';
import { AdminTabsService } from 'app/entities/admin-tabs/service/admin-tabs.service';
import { IAdminGroups } from 'app/entities/admin-groups/admin-groups.model';
import { AdminGroupsService } from 'app/entities/admin-groups/service/admin-groups.service';

import { AdminGroupsTabsUpdateComponent } from './admin-groups-tabs-update.component';

describe('AdminGroupsTabs Management Update Component', () => {
  let comp: AdminGroupsTabsUpdateComponent;
  let fixture: ComponentFixture<AdminGroupsTabsUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let adminGroupsTabsService: AdminGroupsTabsService;
  let adminTabsService: AdminTabsService;
  let adminGroupsService: AdminGroupsService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [AdminGroupsTabsUpdateComponent],
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
      .overrideTemplate(AdminGroupsTabsUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(AdminGroupsTabsUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    adminGroupsTabsService = TestBed.inject(AdminGroupsTabsService);
    adminTabsService = TestBed.inject(AdminTabsService);
    adminGroupsService = TestBed.inject(AdminGroupsService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call tabId query and add missing value', () => {
      const adminGroupsTabs: IAdminGroupsTabs = { id: 456 };
      const tabId: IAdminTabs = { id: 14659 };
      adminGroupsTabs.tabId = tabId;

      const tabIdCollection: IAdminTabs[] = [{ id: 42629 }];
      jest.spyOn(adminTabsService, 'query').mockReturnValue(of(new HttpResponse({ body: tabIdCollection })));
      const expectedCollection: IAdminTabs[] = [tabId, ...tabIdCollection];
      jest.spyOn(adminTabsService, 'addAdminTabsToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ adminGroupsTabs });
      comp.ngOnInit();

      expect(adminTabsService.query).toHaveBeenCalled();
      expect(adminTabsService.addAdminTabsToCollectionIfMissing).toHaveBeenCalledWith(tabIdCollection, tabId);
      expect(comp.tabIdsCollection).toEqual(expectedCollection);
    });

    it('Should call AdminGroups query and add missing value', () => {
      const adminGroupsTabs: IAdminGroupsTabs = { id: 456 };
      const groupId: IAdminGroups = { id: 10661 };
      adminGroupsTabs.groupId = groupId;

      const adminGroupsCollection: IAdminGroups[] = [{ id: 80215 }];
      jest.spyOn(adminGroupsService, 'query').mockReturnValue(of(new HttpResponse({ body: adminGroupsCollection })));
      const additionalAdminGroups = [groupId];
      const expectedCollection: IAdminGroups[] = [...additionalAdminGroups, ...adminGroupsCollection];
      jest.spyOn(adminGroupsService, 'addAdminGroupsToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ adminGroupsTabs });
      comp.ngOnInit();

      expect(adminGroupsService.query).toHaveBeenCalled();
      expect(adminGroupsService.addAdminGroupsToCollectionIfMissing).toHaveBeenCalledWith(adminGroupsCollection, ...additionalAdminGroups);
      expect(comp.adminGroupsSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const adminGroupsTabs: IAdminGroupsTabs = { id: 456 };
      const tabId: IAdminTabs = { id: 1277 };
      adminGroupsTabs.tabId = tabId;
      const groupId: IAdminGroups = { id: 26087 };
      adminGroupsTabs.groupId = groupId;

      activatedRoute.data = of({ adminGroupsTabs });
      comp.ngOnInit();

      expect(comp.editForm.value).toEqual(expect.objectContaining(adminGroupsTabs));
      expect(comp.tabIdsCollection).toContain(tabId);
      expect(comp.adminGroupsSharedCollection).toContain(groupId);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<AdminGroupsTabs>>();
      const adminGroupsTabs = { id: 123 };
      jest.spyOn(adminGroupsTabsService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ adminGroupsTabs });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: adminGroupsTabs }));
      saveSubject.complete();

      // THEN
      expect(comp.previousState).toHaveBeenCalled();
      expect(adminGroupsTabsService.update).toHaveBeenCalledWith(adminGroupsTabs);
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<AdminGroupsTabs>>();
      const adminGroupsTabs = new AdminGroupsTabs();
      jest.spyOn(adminGroupsTabsService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ adminGroupsTabs });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: adminGroupsTabs }));
      saveSubject.complete();

      // THEN
      expect(adminGroupsTabsService.create).toHaveBeenCalledWith(adminGroupsTabs);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<AdminGroupsTabs>>();
      const adminGroupsTabs = { id: 123 };
      jest.spyOn(adminGroupsTabsService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ adminGroupsTabs });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(adminGroupsTabsService.update).toHaveBeenCalledWith(adminGroupsTabs);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Tracking relationships identifiers', () => {
    describe('trackAdminTabsById', () => {
      it('Should return tracked AdminTabs primary key', () => {
        const entity = { id: 123 };
        const trackResult = comp.trackAdminTabsById(0, entity);
        expect(trackResult).toEqual(entity.id);
      });
    });

    describe('trackAdminGroupsById', () => {
      it('Should return tracked AdminGroups primary key', () => {
        const entity = { id: 123 };
        const trackResult = comp.trackAdminGroupsById(0, entity);
        expect(trackResult).toEqual(entity.id);
      });
    });
  });
});
