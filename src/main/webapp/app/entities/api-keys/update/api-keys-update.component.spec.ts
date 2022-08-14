import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { ApiKeysService } from '../service/api-keys.service';
import { IApiKeys, ApiKeys } from '../api-keys.model';
import { ISiteUsers } from 'app/entities/site-users/site-users.model';
import { SiteUsersService } from 'app/entities/site-users/service/site-users.service';

import { ApiKeysUpdateComponent } from './api-keys-update.component';

describe('ApiKeys Management Update Component', () => {
  let comp: ApiKeysUpdateComponent;
  let fixture: ComponentFixture<ApiKeysUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let apiKeysService: ApiKeysService;
  let siteUsersService: SiteUsersService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [ApiKeysUpdateComponent],
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
      .overrideTemplate(ApiKeysUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(ApiKeysUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    apiKeysService = TestBed.inject(ApiKeysService);
    siteUsersService = TestBed.inject(SiteUsersService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call siteUser query and add missing value', () => {
      const apiKeys: IApiKeys = { id: 456 };
      const siteUser: ISiteUsers = { id: 18435 };
      apiKeys.siteUser = siteUser;

      const siteUserCollection: ISiteUsers[] = [{ id: 47863 }];
      jest.spyOn(siteUsersService, 'query').mockReturnValue(of(new HttpResponse({ body: siteUserCollection })));
      const expectedCollection: ISiteUsers[] = [siteUser, ...siteUserCollection];
      jest.spyOn(siteUsersService, 'addSiteUsersToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ apiKeys });
      comp.ngOnInit();

      expect(siteUsersService.query).toHaveBeenCalled();
      expect(siteUsersService.addSiteUsersToCollectionIfMissing).toHaveBeenCalledWith(siteUserCollection, siteUser);
      expect(comp.siteUsersCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const apiKeys: IApiKeys = { id: 456 };
      const siteUser: ISiteUsers = { id: 50061 };
      apiKeys.siteUser = siteUser;

      activatedRoute.data = of({ apiKeys });
      comp.ngOnInit();

      expect(comp.editForm.value).toEqual(expect.objectContaining(apiKeys));
      expect(comp.siteUsersCollection).toContain(siteUser);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ApiKeys>>();
      const apiKeys = { id: 123 };
      jest.spyOn(apiKeysService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ apiKeys });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: apiKeys }));
      saveSubject.complete();

      // THEN
      expect(comp.previousState).toHaveBeenCalled();
      expect(apiKeysService.update).toHaveBeenCalledWith(apiKeys);
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ApiKeys>>();
      const apiKeys = new ApiKeys();
      jest.spyOn(apiKeysService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ apiKeys });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: apiKeys }));
      saveSubject.complete();

      // THEN
      expect(apiKeysService.create).toHaveBeenCalledWith(apiKeys);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ApiKeys>>();
      const apiKeys = { id: 123 };
      jest.spyOn(apiKeysService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ apiKeys });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(apiKeysService.update).toHaveBeenCalledWith(apiKeys);
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
