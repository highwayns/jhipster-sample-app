import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { AdminControlsService } from '../service/admin-controls.service';
import { IAdminControls, AdminControls } from '../admin-controls.model';
import { IAdminPages } from 'app/entities/admin-pages/admin-pages.model';
import { AdminPagesService } from 'app/entities/admin-pages/service/admin-pages.service';
import { IAdminTabs } from 'app/entities/admin-tabs/admin-tabs.model';
import { AdminTabsService } from 'app/entities/admin-tabs/service/admin-tabs.service';

import { AdminControlsUpdateComponent } from './admin-controls-update.component';

describe('AdminControls Management Update Component', () => {
  let comp: AdminControlsUpdateComponent;
  let fixture: ComponentFixture<AdminControlsUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let adminControlsService: AdminControlsService;
  let adminPagesService: AdminPagesService;
  let adminTabsService: AdminTabsService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [AdminControlsUpdateComponent],
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
      .overrideTemplate(AdminControlsUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(AdminControlsUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    adminControlsService = TestBed.inject(AdminControlsService);
    adminPagesService = TestBed.inject(AdminPagesService);
    adminTabsService = TestBed.inject(AdminTabsService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call pageId query and add missing value', () => {
      const adminControls: IAdminControls = { id: 456 };
      const pageId: IAdminPages = { id: 31509 };
      adminControls.pageId = pageId;

      const pageIdCollection: IAdminPages[] = [{ id: 55081 }];
      jest.spyOn(adminPagesService, 'query').mockReturnValue(of(new HttpResponse({ body: pageIdCollection })));
      const expectedCollection: IAdminPages[] = [pageId, ...pageIdCollection];
      jest.spyOn(adminPagesService, 'addAdminPagesToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ adminControls });
      comp.ngOnInit();

      expect(adminPagesService.query).toHaveBeenCalled();
      expect(adminPagesService.addAdminPagesToCollectionIfMissing).toHaveBeenCalledWith(pageIdCollection, pageId);
      expect(comp.pageIdsCollection).toEqual(expectedCollection);
    });

    it('Should call tabId query and add missing value', () => {
      const adminControls: IAdminControls = { id: 456 };
      const tabId: IAdminTabs = { id: 32921 };
      adminControls.tabId = tabId;

      const tabIdCollection: IAdminTabs[] = [{ id: 77180 }];
      jest.spyOn(adminTabsService, 'query').mockReturnValue(of(new HttpResponse({ body: tabIdCollection })));
      const expectedCollection: IAdminTabs[] = [tabId, ...tabIdCollection];
      jest.spyOn(adminTabsService, 'addAdminTabsToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ adminControls });
      comp.ngOnInit();

      expect(adminTabsService.query).toHaveBeenCalled();
      expect(adminTabsService.addAdminTabsToCollectionIfMissing).toHaveBeenCalledWith(tabIdCollection, tabId);
      expect(comp.tabIdsCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const adminControls: IAdminControls = { id: 456 };
      const pageId: IAdminPages = { id: 98378 };
      adminControls.pageId = pageId;
      const tabId: IAdminTabs = { id: 13870 };
      adminControls.tabId = tabId;

      activatedRoute.data = of({ adminControls });
      comp.ngOnInit();

      expect(comp.editForm.value).toEqual(expect.objectContaining(adminControls));
      expect(comp.pageIdsCollection).toContain(pageId);
      expect(comp.tabIdsCollection).toContain(tabId);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<AdminControls>>();
      const adminControls = { id: 123 };
      jest.spyOn(adminControlsService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ adminControls });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: adminControls }));
      saveSubject.complete();

      // THEN
      expect(comp.previousState).toHaveBeenCalled();
      expect(adminControlsService.update).toHaveBeenCalledWith(adminControls);
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<AdminControls>>();
      const adminControls = new AdminControls();
      jest.spyOn(adminControlsService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ adminControls });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: adminControls }));
      saveSubject.complete();

      // THEN
      expect(adminControlsService.create).toHaveBeenCalledWith(adminControls);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<AdminControls>>();
      const adminControls = { id: 123 };
      jest.spyOn(adminControlsService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ adminControls });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(adminControlsService.update).toHaveBeenCalledWith(adminControls);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Tracking relationships identifiers', () => {
    describe('trackAdminPagesById', () => {
      it('Should return tracked AdminPages primary key', () => {
        const entity = { id: 123 };
        const trackResult = comp.trackAdminPagesById(0, entity);
        expect(trackResult).toEqual(entity.id);
      });
    });

    describe('trackAdminTabsById', () => {
      it('Should return tracked AdminTabs primary key', () => {
        const entity = { id: 123 };
        const trackResult = comp.trackAdminTabsById(0, entity);
        expect(trackResult).toEqual(entity.id);
      });
    });
  });
});
