import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { SiteUsersCatchService } from '../service/site-users-catch.service';
import { ISiteUsersCatch, SiteUsersCatch } from '../site-users-catch.model';
import { ISiteUsers } from 'app/entities/site-users/site-users.model';
import { SiteUsersService } from 'app/entities/site-users/service/site-users.service';

import { SiteUsersCatchUpdateComponent } from './site-users-catch-update.component';

describe('SiteUsersCatch Management Update Component', () => {
  let comp: SiteUsersCatchUpdateComponent;
  let fixture: ComponentFixture<SiteUsersCatchUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let siteUsersCatchService: SiteUsersCatchService;
  let siteUsersService: SiteUsersService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [SiteUsersCatchUpdateComponent],
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
      .overrideTemplate(SiteUsersCatchUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(SiteUsersCatchUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    siteUsersCatchService = TestBed.inject(SiteUsersCatchService);
    siteUsersService = TestBed.inject(SiteUsersService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call siteUser query and add missing value', () => {
      const siteUsersCatch: ISiteUsersCatch = { id: 456 };
      const siteUser: ISiteUsers = { id: 46138 };
      siteUsersCatch.siteUser = siteUser;

      const siteUserCollection: ISiteUsers[] = [{ id: 19783 }];
      jest.spyOn(siteUsersService, 'query').mockReturnValue(of(new HttpResponse({ body: siteUserCollection })));
      const expectedCollection: ISiteUsers[] = [siteUser, ...siteUserCollection];
      jest.spyOn(siteUsersService, 'addSiteUsersToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ siteUsersCatch });
      comp.ngOnInit();

      expect(siteUsersService.query).toHaveBeenCalled();
      expect(siteUsersService.addSiteUsersToCollectionIfMissing).toHaveBeenCalledWith(siteUserCollection, siteUser);
      expect(comp.siteUsersCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const siteUsersCatch: ISiteUsersCatch = { id: 456 };
      const siteUser: ISiteUsers = { id: 32954 };
      siteUsersCatch.siteUser = siteUser;

      activatedRoute.data = of({ siteUsersCatch });
      comp.ngOnInit();

      expect(comp.editForm.value).toEqual(expect.objectContaining(siteUsersCatch));
      expect(comp.siteUsersCollection).toContain(siteUser);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<SiteUsersCatch>>();
      const siteUsersCatch = { id: 123 };
      jest.spyOn(siteUsersCatchService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ siteUsersCatch });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: siteUsersCatch }));
      saveSubject.complete();

      // THEN
      expect(comp.previousState).toHaveBeenCalled();
      expect(siteUsersCatchService.update).toHaveBeenCalledWith(siteUsersCatch);
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<SiteUsersCatch>>();
      const siteUsersCatch = new SiteUsersCatch();
      jest.spyOn(siteUsersCatchService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ siteUsersCatch });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: siteUsersCatch }));
      saveSubject.complete();

      // THEN
      expect(siteUsersCatchService.create).toHaveBeenCalledWith(siteUsersCatch);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<SiteUsersCatch>>();
      const siteUsersCatch = { id: 123 };
      jest.spyOn(siteUsersCatchService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ siteUsersCatch });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(siteUsersCatchService.update).toHaveBeenCalledWith(siteUsersCatch);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Tracking relationships identifiers', () => {
    describe('trackSiteUsersById', () => {
      it('Should return tracked SiteUsers primary key', () => {
        const entity = { id: 123 };
        const trackResult = comp.trackSiteUsersById(0, entity);
        expect(trackResult).toEqual(entity.id);
      });
    });
  });
});
