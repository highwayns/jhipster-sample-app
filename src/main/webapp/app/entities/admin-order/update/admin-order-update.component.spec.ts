import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { AdminOrderService } from '../service/admin-order.service';
import { IAdminOrder, AdminOrder } from '../admin-order.model';
import { IAdminControls } from 'app/entities/admin-controls/admin-controls.model';
import { AdminControlsService } from 'app/entities/admin-controls/service/admin-controls.service';
import { IAdminUsers } from 'app/entities/admin-users/admin-users.model';
import { AdminUsersService } from 'app/entities/admin-users/service/admin-users.service';

import { AdminOrderUpdateComponent } from './admin-order-update.component';

describe('AdminOrder Management Update Component', () => {
  let comp: AdminOrderUpdateComponent;
  let fixture: ComponentFixture<AdminOrderUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let adminOrderService: AdminOrderService;
  let adminControlsService: AdminControlsService;
  let adminUsersService: AdminUsersService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [AdminOrderUpdateComponent],
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
      .overrideTemplate(AdminOrderUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(AdminOrderUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    adminOrderService = TestBed.inject(AdminOrderService);
    adminControlsService = TestBed.inject(AdminControlsService);
    adminUsersService = TestBed.inject(AdminUsersService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call AdminControls query and add missing value', () => {
      const adminOrder: IAdminOrder = { id: 456 };
      const controlId: IAdminControls = { id: 85989 };
      adminOrder.controlId = controlId;

      const adminControlsCollection: IAdminControls[] = [{ id: 95070 }];
      jest.spyOn(adminControlsService, 'query').mockReturnValue(of(new HttpResponse({ body: adminControlsCollection })));
      const additionalAdminControls = [controlId];
      const expectedCollection: IAdminControls[] = [...additionalAdminControls, ...adminControlsCollection];
      jest.spyOn(adminControlsService, 'addAdminControlsToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ adminOrder });
      comp.ngOnInit();

      expect(adminControlsService.query).toHaveBeenCalled();
      expect(adminControlsService.addAdminControlsToCollectionIfMissing).toHaveBeenCalledWith(
        adminControlsCollection,
        ...additionalAdminControls
      );
      expect(comp.adminControlsSharedCollection).toEqual(expectedCollection);
    });

    it('Should call AdminUsers query and add missing value', () => {
      const adminOrder: IAdminOrder = { id: 456 };
      const userId: IAdminUsers = { id: 14446 };
      adminOrder.userId = userId;

      const adminUsersCollection: IAdminUsers[] = [{ id: 8899 }];
      jest.spyOn(adminUsersService, 'query').mockReturnValue(of(new HttpResponse({ body: adminUsersCollection })));
      const additionalAdminUsers = [userId];
      const expectedCollection: IAdminUsers[] = [...additionalAdminUsers, ...adminUsersCollection];
      jest.spyOn(adminUsersService, 'addAdminUsersToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ adminOrder });
      comp.ngOnInit();

      expect(adminUsersService.query).toHaveBeenCalled();
      expect(adminUsersService.addAdminUsersToCollectionIfMissing).toHaveBeenCalledWith(adminUsersCollection, ...additionalAdminUsers);
      expect(comp.adminUsersSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const adminOrder: IAdminOrder = { id: 456 };
      const controlId: IAdminControls = { id: 62059 };
      adminOrder.controlId = controlId;
      const userId: IAdminUsers = { id: 97661 };
      adminOrder.userId = userId;

      activatedRoute.data = of({ adminOrder });
      comp.ngOnInit();

      expect(comp.editForm.value).toEqual(expect.objectContaining(adminOrder));
      expect(comp.adminControlsSharedCollection).toContain(controlId);
      expect(comp.adminUsersSharedCollection).toContain(userId);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<AdminOrder>>();
      const adminOrder = { id: 123 };
      jest.spyOn(adminOrderService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ adminOrder });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: adminOrder }));
      saveSubject.complete();

      // THEN
      expect(comp.previousState).toHaveBeenCalled();
      expect(adminOrderService.update).toHaveBeenCalledWith(adminOrder);
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<AdminOrder>>();
      const adminOrder = new AdminOrder();
      jest.spyOn(adminOrderService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ adminOrder });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: adminOrder }));
      saveSubject.complete();

      // THEN
      expect(adminOrderService.create).toHaveBeenCalledWith(adminOrder);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<AdminOrder>>();
      const adminOrder = { id: 123 };
      jest.spyOn(adminOrderService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ adminOrder });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(adminOrderService.update).toHaveBeenCalledWith(adminOrder);
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

    describe('trackAdminUsersById', () => {
      it('Should return tracked AdminUsers primary key', () => {
        const entity = { id: 123 };
        const trackResult = comp.trackAdminUsersById(0, entity);
        expect(trackResult).toEqual(entity.id);
      });
    });
  });
});
