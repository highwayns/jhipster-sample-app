import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { AdminControlsMethodsService } from '../service/admin-controls-methods.service';
import { IAdminControlsMethods, AdminControlsMethods } from '../admin-controls-methods.model';
import { IAdminControls } from 'app/entities/admin-controls/admin-controls.model';
import { AdminControlsService } from 'app/entities/admin-controls/service/admin-controls.service';

import { AdminControlsMethodsUpdateComponent } from './admin-controls-methods-update.component';

describe('AdminControlsMethods Management Update Component', () => {
  let comp: AdminControlsMethodsUpdateComponent;
  let fixture: ComponentFixture<AdminControlsMethodsUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let adminControlsMethodsService: AdminControlsMethodsService;
  let adminControlsService: AdminControlsService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [AdminControlsMethodsUpdateComponent],
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
      .overrideTemplate(AdminControlsMethodsUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(AdminControlsMethodsUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    adminControlsMethodsService = TestBed.inject(AdminControlsMethodsService);
    adminControlsService = TestBed.inject(AdminControlsService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call AdminControls query and add missing value', () => {
      const adminControlsMethods: IAdminControlsMethods = { id: 456 };
      const controlId: IAdminControls = { id: 98106 };
      adminControlsMethods.controlId = controlId;

      const adminControlsCollection: IAdminControls[] = [{ id: 71000 }];
      jest.spyOn(adminControlsService, 'query').mockReturnValue(of(new HttpResponse({ body: adminControlsCollection })));
      const additionalAdminControls = [controlId];
      const expectedCollection: IAdminControls[] = [...additionalAdminControls, ...adminControlsCollection];
      jest.spyOn(adminControlsService, 'addAdminControlsToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ adminControlsMethods });
      comp.ngOnInit();

      expect(adminControlsService.query).toHaveBeenCalled();
      expect(adminControlsService.addAdminControlsToCollectionIfMissing).toHaveBeenCalledWith(
        adminControlsCollection,
        ...additionalAdminControls
      );
      expect(comp.adminControlsSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const adminControlsMethods: IAdminControlsMethods = { id: 456 };
      const controlId: IAdminControls = { id: 93160 };
      adminControlsMethods.controlId = controlId;

      activatedRoute.data = of({ adminControlsMethods });
      comp.ngOnInit();

      expect(comp.editForm.value).toEqual(expect.objectContaining(adminControlsMethods));
      expect(comp.adminControlsSharedCollection).toContain(controlId);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<AdminControlsMethods>>();
      const adminControlsMethods = { id: 123 };
      jest.spyOn(adminControlsMethodsService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ adminControlsMethods });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: adminControlsMethods }));
      saveSubject.complete();

      // THEN
      expect(comp.previousState).toHaveBeenCalled();
      expect(adminControlsMethodsService.update).toHaveBeenCalledWith(adminControlsMethods);
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<AdminControlsMethods>>();
      const adminControlsMethods = new AdminControlsMethods();
      jest.spyOn(adminControlsMethodsService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ adminControlsMethods });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: adminControlsMethods }));
      saveSubject.complete();

      // THEN
      expect(adminControlsMethodsService.create).toHaveBeenCalledWith(adminControlsMethods);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<AdminControlsMethods>>();
      const adminControlsMethods = { id: 123 };
      jest.spyOn(adminControlsMethodsService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ adminControlsMethods });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(adminControlsMethodsService.update).toHaveBeenCalledWith(adminControlsMethods);
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
  });
});
