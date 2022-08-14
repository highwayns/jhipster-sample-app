import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { AdminCronService } from '../service/admin-cron.service';
import { IAdminCron, AdminCron } from '../admin-cron.model';
import { IAdminControls } from 'app/entities/admin-controls/admin-controls.model';
import { AdminControlsService } from 'app/entities/admin-controls/service/admin-controls.service';
import { IAdminControlsMethods } from 'app/entities/admin-controls-methods/admin-controls-methods.model';
import { AdminControlsMethodsService } from 'app/entities/admin-controls-methods/service/admin-controls-methods.service';

import { AdminCronUpdateComponent } from './admin-cron-update.component';

describe('AdminCron Management Update Component', () => {
  let comp: AdminCronUpdateComponent;
  let fixture: ComponentFixture<AdminCronUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let adminCronService: AdminCronService;
  let adminControlsService: AdminControlsService;
  let adminControlsMethodsService: AdminControlsMethodsService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [AdminCronUpdateComponent],
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
      .overrideTemplate(AdminCronUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(AdminCronUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    adminCronService = TestBed.inject(AdminCronService);
    adminControlsService = TestBed.inject(AdminControlsService);
    adminControlsMethodsService = TestBed.inject(AdminControlsMethodsService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call AdminControls query and add missing value', () => {
      const adminCron: IAdminCron = { id: 456 };
      const controlId: IAdminControls = { id: 41849 };
      adminCron.controlId = controlId;

      const adminControlsCollection: IAdminControls[] = [{ id: 65507 }];
      jest.spyOn(adminControlsService, 'query').mockReturnValue(of(new HttpResponse({ body: adminControlsCollection })));
      const additionalAdminControls = [controlId];
      const expectedCollection: IAdminControls[] = [...additionalAdminControls, ...adminControlsCollection];
      jest.spyOn(adminControlsService, 'addAdminControlsToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ adminCron });
      comp.ngOnInit();

      expect(adminControlsService.query).toHaveBeenCalled();
      expect(adminControlsService.addAdminControlsToCollectionIfMissing).toHaveBeenCalledWith(
        adminControlsCollection,
        ...additionalAdminControls
      );
      expect(comp.adminControlsSharedCollection).toEqual(expectedCollection);
    });

    it('Should call AdminControlsMethods query and add missing value', () => {
      const adminCron: IAdminCron = { id: 456 };
      const methodId: IAdminControlsMethods = { id: 28761 };
      adminCron.methodId = methodId;

      const adminControlsMethodsCollection: IAdminControlsMethods[] = [{ id: 91632 }];
      jest.spyOn(adminControlsMethodsService, 'query').mockReturnValue(of(new HttpResponse({ body: adminControlsMethodsCollection })));
      const additionalAdminControlsMethods = [methodId];
      const expectedCollection: IAdminControlsMethods[] = [...additionalAdminControlsMethods, ...adminControlsMethodsCollection];
      jest.spyOn(adminControlsMethodsService, 'addAdminControlsMethodsToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ adminCron });
      comp.ngOnInit();

      expect(adminControlsMethodsService.query).toHaveBeenCalled();
      expect(adminControlsMethodsService.addAdminControlsMethodsToCollectionIfMissing).toHaveBeenCalledWith(
        adminControlsMethodsCollection,
        ...additionalAdminControlsMethods
      );
      expect(comp.adminControlsMethodsSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const adminCron: IAdminCron = { id: 456 };
      const controlId: IAdminControls = { id: 48138 };
      adminCron.controlId = controlId;
      const methodId: IAdminControlsMethods = { id: 33164 };
      adminCron.methodId = methodId;

      activatedRoute.data = of({ adminCron });
      comp.ngOnInit();

      expect(comp.editForm.value).toEqual(expect.objectContaining(adminCron));
      expect(comp.adminControlsSharedCollection).toContain(controlId);
      expect(comp.adminControlsMethodsSharedCollection).toContain(methodId);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<AdminCron>>();
      const adminCron = { id: 123 };
      jest.spyOn(adminCronService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ adminCron });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: adminCron }));
      saveSubject.complete();

      // THEN
      expect(comp.previousState).toHaveBeenCalled();
      expect(adminCronService.update).toHaveBeenCalledWith(adminCron);
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<AdminCron>>();
      const adminCron = new AdminCron();
      jest.spyOn(adminCronService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ adminCron });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: adminCron }));
      saveSubject.complete();

      // THEN
      expect(adminCronService.create).toHaveBeenCalledWith(adminCron);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<AdminCron>>();
      const adminCron = { id: 123 };
      jest.spyOn(adminCronService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ adminCron });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(adminCronService.update).toHaveBeenCalledWith(adminCron);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Tracking relationships identifiers', () => {
    describe('trackAdminControlsById', () => {
      it('Should return tracked AdminControls primary key', () => {
        const entity = { id: 123 };
        const trackResult = comp.trackAdminControlsById(0, entity);
        expect(trackResult).toEqual(entity.id);
      });
    });

    describe('trackAdminControlsMethodsById', () => {
      it('Should return tracked AdminControlsMethods primary key', () => {
        const entity = { id: 123 };
        const trackResult = comp.trackAdminControlsMethodsById(0, entity);
        expect(trackResult).toEqual(entity.id);
      });
    });
  });
});
