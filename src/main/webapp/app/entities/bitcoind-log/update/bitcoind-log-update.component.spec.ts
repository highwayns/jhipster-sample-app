import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { BitcoindLogService } from '../service/bitcoind-log.service';
import { IBitcoindLog, BitcoindLog } from '../bitcoind-log.model';
import { ISiteUsers } from 'app/entities/site-users/site-users.model';
import { SiteUsersService } from 'app/entities/site-users/service/site-users.service';

import { BitcoindLogUpdateComponent } from './bitcoind-log-update.component';

describe('BitcoindLog Management Update Component', () => {
  let comp: BitcoindLogUpdateComponent;
  let fixture: ComponentFixture<BitcoindLogUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let bitcoindLogService: BitcoindLogService;
  let siteUsersService: SiteUsersService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [BitcoindLogUpdateComponent],
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
      .overrideTemplate(BitcoindLogUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(BitcoindLogUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    bitcoindLogService = TestBed.inject(BitcoindLogService);
    siteUsersService = TestBed.inject(SiteUsersService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call siteUser query and add missing value', () => {
      const bitcoindLog: IBitcoindLog = { id: 456 };
      const siteUser: ISiteUsers = { id: 62464 };
      bitcoindLog.siteUser = siteUser;

      const siteUserCollection: ISiteUsers[] = [{ id: 3038 }];
      jest.spyOn(siteUsersService, 'query').mockReturnValue(of(new HttpResponse({ body: siteUserCollection })));
      const expectedCollection: ISiteUsers[] = [siteUser, ...siteUserCollection];
      jest.spyOn(siteUsersService, 'addSiteUsersToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ bitcoindLog });
      comp.ngOnInit();

      expect(siteUsersService.query).toHaveBeenCalled();
      expect(siteUsersService.addSiteUsersToCollectionIfMissing).toHaveBeenCalledWith(siteUserCollection, siteUser);
      expect(comp.siteUsersCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const bitcoindLog: IBitcoindLog = { id: 456 };
      const siteUser: ISiteUsers = { id: 68660 };
      bitcoindLog.siteUser = siteUser;

      activatedRoute.data = of({ bitcoindLog });
      comp.ngOnInit();

      expect(comp.editForm.value).toEqual(expect.objectContaining(bitcoindLog));
      expect(comp.siteUsersCollection).toContain(siteUser);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<BitcoindLog>>();
      const bitcoindLog = { id: 123 };
      jest.spyOn(bitcoindLogService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ bitcoindLog });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: bitcoindLog }));
      saveSubject.complete();

      // THEN
      expect(comp.previousState).toHaveBeenCalled();
      expect(bitcoindLogService.update).toHaveBeenCalledWith(bitcoindLog);
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<BitcoindLog>>();
      const bitcoindLog = new BitcoindLog();
      jest.spyOn(bitcoindLogService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ bitcoindLog });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: bitcoindLog }));
      saveSubject.complete();

      // THEN
      expect(bitcoindLogService.create).toHaveBeenCalledWith(bitcoindLog);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<BitcoindLog>>();
      const bitcoindLog = { id: 123 };
      jest.spyOn(bitcoindLogService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ bitcoindLog });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(bitcoindLogService.update).toHaveBeenCalledWith(bitcoindLog);
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
