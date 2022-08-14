import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { ChangeSettingsService } from '../service/change-settings.service';
import { IChangeSettings, ChangeSettings } from '../change-settings.model';
import { ISiteUsers } from 'app/entities/site-users/site-users.model';
import { SiteUsersService } from 'app/entities/site-users/service/site-users.service';

import { ChangeSettingsUpdateComponent } from './change-settings-update.component';

describe('ChangeSettings Management Update Component', () => {
  let comp: ChangeSettingsUpdateComponent;
  let fixture: ComponentFixture<ChangeSettingsUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let changeSettingsService: ChangeSettingsService;
  let siteUsersService: SiteUsersService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [ChangeSettingsUpdateComponent],
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
      .overrideTemplate(ChangeSettingsUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(ChangeSettingsUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    changeSettingsService = TestBed.inject(ChangeSettingsService);
    siteUsersService = TestBed.inject(SiteUsersService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call siteUser query and add missing value', () => {
      const changeSettings: IChangeSettings = { id: 456 };
      const siteUser: ISiteUsers = { id: 42742 };
      changeSettings.siteUser = siteUser;

      const siteUserCollection: ISiteUsers[] = [{ id: 81862 }];
      jest.spyOn(siteUsersService, 'query').mockReturnValue(of(new HttpResponse({ body: siteUserCollection })));
      const expectedCollection: ISiteUsers[] = [siteUser, ...siteUserCollection];
      jest.spyOn(siteUsersService, 'addSiteUsersToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ changeSettings });
      comp.ngOnInit();

      expect(siteUsersService.query).toHaveBeenCalled();
      expect(siteUsersService.addSiteUsersToCollectionIfMissing).toHaveBeenCalledWith(siteUserCollection, siteUser);
      expect(comp.siteUsersCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const changeSettings: IChangeSettings = { id: 456 };
      const siteUser: ISiteUsers = { id: 2420 };
      changeSettings.siteUser = siteUser;

      activatedRoute.data = of({ changeSettings });
      comp.ngOnInit();

      expect(comp.editForm.value).toEqual(expect.objectContaining(changeSettings));
      expect(comp.siteUsersCollection).toContain(siteUser);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ChangeSettings>>();
      const changeSettings = { id: 123 };
      jest.spyOn(changeSettingsService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ changeSettings });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: changeSettings }));
      saveSubject.complete();

      // THEN
      expect(comp.previousState).toHaveBeenCalled();
      expect(changeSettingsService.update).toHaveBeenCalledWith(changeSettings);
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ChangeSettings>>();
      const changeSettings = new ChangeSettings();
      jest.spyOn(changeSettingsService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ changeSettings });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: changeSettings }));
      saveSubject.complete();

      // THEN
      expect(changeSettingsService.create).toHaveBeenCalledWith(changeSettings);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ChangeSettings>>();
      const changeSettings = { id: 123 };
      jest.spyOn(changeSettingsService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ changeSettings });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(changeSettingsService.update).toHaveBeenCalledWith(changeSettings);
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
