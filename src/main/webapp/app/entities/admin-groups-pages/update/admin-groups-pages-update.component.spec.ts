import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { AdminGroupsPagesService } from '../service/admin-groups-pages.service';
import { IAdminGroupsPages, AdminGroupsPages } from '../admin-groups-pages.model';
import { IAdminPages } from 'app/entities/admin-pages/admin-pages.model';
import { AdminPagesService } from 'app/entities/admin-pages/service/admin-pages.service';
import { IAdminGroups } from 'app/entities/admin-groups/admin-groups.model';
import { AdminGroupsService } from 'app/entities/admin-groups/service/admin-groups.service';

import { AdminGroupsPagesUpdateComponent } from './admin-groups-pages-update.component';

describe('AdminGroupsPages Management Update Component', () => {
  let comp: AdminGroupsPagesUpdateComponent;
  let fixture: ComponentFixture<AdminGroupsPagesUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let adminGroupsPagesService: AdminGroupsPagesService;
  let adminPagesService: AdminPagesService;
  let adminGroupsService: AdminGroupsService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [AdminGroupsPagesUpdateComponent],
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
      .overrideTemplate(AdminGroupsPagesUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(AdminGroupsPagesUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    adminGroupsPagesService = TestBed.inject(AdminGroupsPagesService);
    adminPagesService = TestBed.inject(AdminPagesService);
    adminGroupsService = TestBed.inject(AdminGroupsService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call pageId query and add missing value', () => {
      const adminGroupsPages: IAdminGroupsPages = { id: 456 };
      const pageId: IAdminPages = { id: 86355 };
      adminGroupsPages.pageId = pageId;

      const pageIdCollection: IAdminPages[] = [{ id: 68935 }];
      jest.spyOn(adminPagesService, 'query').mockReturnValue(of(new HttpResponse({ body: pageIdCollection })));
      const expectedCollection: IAdminPages[] = [pageId, ...pageIdCollection];
      jest.spyOn(adminPagesService, 'addAdminPagesToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ adminGroupsPages });
      comp.ngOnInit();

      expect(adminPagesService.query).toHaveBeenCalled();
      expect(adminPagesService.addAdminPagesToCollectionIfMissing).toHaveBeenCalledWith(pageIdCollection, pageId);
      expect(comp.pageIdsCollection).toEqual(expectedCollection);
    });

    it('Should call AdminGroups query and add missing value', () => {
      const adminGroupsPages: IAdminGroupsPages = { id: 456 };
      const groupId: IAdminGroups = { id: 80538 };
      adminGroupsPages.groupId = groupId;

      const adminGroupsCollection: IAdminGroups[] = [{ id: 80515 }];
      jest.spyOn(adminGroupsService, 'query').mockReturnValue(of(new HttpResponse({ body: adminGroupsCollection })));
      const additionalAdminGroups = [groupId];
      const expectedCollection: IAdminGroups[] = [...additionalAdminGroups, ...adminGroupsCollection];
      jest.spyOn(adminGroupsService, 'addAdminGroupsToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ adminGroupsPages });
      comp.ngOnInit();

      expect(adminGroupsService.query).toHaveBeenCalled();
      expect(adminGroupsService.addAdminGroupsToCollectionIfMissing).toHaveBeenCalledWith(adminGroupsCollection, ...additionalAdminGroups);
      expect(comp.adminGroupsSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const adminGroupsPages: IAdminGroupsPages = { id: 456 };
      const pageId: IAdminPages = { id: 23686 };
      adminGroupsPages.pageId = pageId;
      const groupId: IAdminGroups = { id: 74017 };
      adminGroupsPages.groupId = groupId;

      activatedRoute.data = of({ adminGroupsPages });
      comp.ngOnInit();

      expect(comp.editForm.value).toEqual(expect.objectContaining(adminGroupsPages));
      expect(comp.pageIdsCollection).toContain(pageId);
      expect(comp.adminGroupsSharedCollection).toContain(groupId);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<AdminGroupsPages>>();
      const adminGroupsPages = { id: 123 };
      jest.spyOn(adminGroupsPagesService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ adminGroupsPages });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: adminGroupsPages }));
      saveSubject.complete();

      // THEN
      expect(comp.previousState).toHaveBeenCalled();
      expect(adminGroupsPagesService.update).toHaveBeenCalledWith(adminGroupsPages);
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<AdminGroupsPages>>();
      const adminGroupsPages = new AdminGroupsPages();
      jest.spyOn(adminGroupsPagesService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ adminGroupsPages });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: adminGroupsPages }));
      saveSubject.complete();

      // THEN
      expect(adminGroupsPagesService.create).toHaveBeenCalledWith(adminGroupsPages);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<AdminGroupsPages>>();
      const adminGroupsPages = { id: 123 };
      jest.spyOn(adminGroupsPagesService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ adminGroupsPages });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(adminGroupsPagesService.update).toHaveBeenCalledWith(adminGroupsPages);
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

    describe('trackAdminGroupsById', () => {
      it('Should return tracked AdminGroups primary key', () => {
        const entity = { id: 123 };
        const trackResult = comp.trackAdminGroupsById(0, entity);
        expect(trackResult).toEqual(entity.id);
      });
    });
  });
});
