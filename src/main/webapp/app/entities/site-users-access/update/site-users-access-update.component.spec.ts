import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { SiteUsersAccessService } from '../service/site-users-access.service';
import { ISiteUsersAccess, SiteUsersAccess } from '../site-users-access.model';
import { ISiteUsers } from 'app/entities/site-users/site-users.model';
import { SiteUsersService } from 'app/entities/site-users/service/site-users.service';

import { SiteUsersAccessUpdateComponent } from './site-users-access-update.component';

describe('SiteUsersAccess Management Update Component', () => {
  let comp: SiteUsersAccessUpdateComponent;
  let fixture: ComponentFixture<SiteUsersAccessUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let siteUsersAccessService: SiteUsersAccessService;
  let siteUsersService: SiteUsersService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [SiteUsersAccessUpdateComponent],
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
      .overrideTemplate(SiteUsersAccessUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(SiteUsersAccessUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    siteUsersAccessService = TestBed.inject(SiteUsersAccessService);
    siteUsersService = TestBed.inject(SiteUsersService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call siteUser query and add missing value', () => {
      const siteUsersAccess: ISiteUsersAccess = { id: 456 };
      const siteUser: ISiteUsers = { id: 1083 };
      siteUsersAccess.siteUser = siteUser;

      const siteUserCollection: ISiteUsers[] = [{ id: 37637 }];
      jest.spyOn(siteUsersService, 'query').mockReturnValue(of(new HttpResponse({ body: siteUserCollection })));
      const expectedCollection: ISiteUsers[] = [siteUser, ...siteUserCollection];
      jest.spyOn(siteUsersService, 'addSiteUsersToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ siteUsersAccess });
      comp.ngOnInit();

      expect(siteUsersService.query).toHaveBeenCalled();
      expect(siteUsersService.addSiteUsersToCollectionIfMissing).toHaveBeenCalledWith(siteUserCollection, siteUser);
      expect(comp.siteUsersCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const siteUsersAccess: ISiteUsersAccess = { id: 456 };
      const siteUser: ISiteUsers = { id: 57836 };
      siteUsersAccess.siteUser = siteUser;

      activatedRoute.data = of({ siteUsersAccess });
      comp.ngOnInit();

      expect(comp.editForm.value).toEqual(expect.objectContaining(siteUsersAccess));
      expect(comp.siteUsersCollection).toContain(siteUser);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<SiteUsersAccess>>();
      const siteUsersAccess = { id: 123 };
      jest.spyOn(siteUsersAccessService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ siteUsersAccess });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: siteUsersAccess }));
      saveSubject.complete();

      // THEN
      expect(comp.previousState).toHaveBeenCalled();
      expect(siteUsersAccessService.update).toHaveBeenCalledWith(siteUsersAccess);
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<SiteUsersAccess>>();
      const siteUsersAccess = new SiteUsersAccess();
      jest.spyOn(siteUsersAccessService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ siteUsersAccess });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: siteUsersAccess }));
      saveSubject.complete();

      // THEN
      expect(siteUsersAccessService.create).toHaveBeenCalledWith(siteUsersAccess);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<SiteUsersAccess>>();
      const siteUsersAccess = { id: 123 };
      jest.spyOn(siteUsersAccessService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ siteUsersAccess });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(siteUsersAccessService.update).toHaveBeenCalledWith(siteUsersAccess);
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
