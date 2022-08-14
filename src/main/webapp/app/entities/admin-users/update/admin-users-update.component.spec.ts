import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { AdminUsersService } from '../service/admin-users.service';
import { IAdminUsers, AdminUsers } from '../admin-users.model';
import { IIsoCountries } from 'app/entities/iso-countries/iso-countries.model';
import { IsoCountriesService } from 'app/entities/iso-countries/service/iso-countries.service';

import { AdminUsersUpdateComponent } from './admin-users-update.component';

describe('AdminUsers Management Update Component', () => {
  let comp: AdminUsersUpdateComponent;
  let fixture: ComponentFixture<AdminUsersUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let adminUsersService: AdminUsersService;
  let isoCountriesService: IsoCountriesService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [AdminUsersUpdateComponent],
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
      .overrideTemplate(AdminUsersUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(AdminUsersUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    adminUsersService = TestBed.inject(AdminUsersService);
    isoCountriesService = TestBed.inject(IsoCountriesService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call countryId query and add missing value', () => {
      const adminUsers: IAdminUsers = { id: 456 };
      const countryId: IIsoCountries = { id: 18189 };
      adminUsers.countryId = countryId;

      const countryIdCollection: IIsoCountries[] = [{ id: 86364 }];
      jest.spyOn(isoCountriesService, 'query').mockReturnValue(of(new HttpResponse({ body: countryIdCollection })));
      const expectedCollection: IIsoCountries[] = [countryId, ...countryIdCollection];
      jest.spyOn(isoCountriesService, 'addIsoCountriesToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ adminUsers });
      comp.ngOnInit();

      expect(isoCountriesService.query).toHaveBeenCalled();
      expect(isoCountriesService.addIsoCountriesToCollectionIfMissing).toHaveBeenCalledWith(countryIdCollection, countryId);
      expect(comp.countryIdsCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const adminUsers: IAdminUsers = { id: 456 };
      const countryId: IIsoCountries = { id: 10137 };
      adminUsers.countryId = countryId;

      activatedRoute.data = of({ adminUsers });
      comp.ngOnInit();

      expect(comp.editForm.value).toEqual(expect.objectContaining(adminUsers));
      expect(comp.countryIdsCollection).toContain(countryId);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<AdminUsers>>();
      const adminUsers = { id: 123 };
      jest.spyOn(adminUsersService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ adminUsers });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: adminUsers }));
      saveSubject.complete();

      // THEN
      expect(comp.previousState).toHaveBeenCalled();
      expect(adminUsersService.update).toHaveBeenCalledWith(adminUsers);
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<AdminUsers>>();
      const adminUsers = new AdminUsers();
      jest.spyOn(adminUsersService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ adminUsers });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: adminUsers }));
      saveSubject.complete();

      // THEN
      expect(adminUsersService.create).toHaveBeenCalledWith(adminUsers);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<AdminUsers>>();
      const adminUsers = { id: 123 };
      jest.spyOn(adminUsersService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ adminUsers });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(adminUsersService.update).toHaveBeenCalledWith(adminUsers);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Tracking relationships identifiers', () => {
    describe('trackIsoCountriesById', () => {
      it('Should return tracked IsoCountries primary key', () => {
        const entity = { id: 123 };
        const trackResult = comp.trackIsoCountriesById(0, entity);
        expect(trackResult).toEqual(entity.id);
      });
    });
  });
});
