import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { BitcoinAddressesService } from '../service/bitcoin-addresses.service';
import { IBitcoinAddresses, BitcoinAddresses } from '../bitcoin-addresses.model';
import { ISiteUsers } from 'app/entities/site-users/site-users.model';
import { SiteUsersService } from 'app/entities/site-users/service/site-users.service';

import { BitcoinAddressesUpdateComponent } from './bitcoin-addresses-update.component';

describe('BitcoinAddresses Management Update Component', () => {
  let comp: BitcoinAddressesUpdateComponent;
  let fixture: ComponentFixture<BitcoinAddressesUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let bitcoinAddressesService: BitcoinAddressesService;
  let siteUsersService: SiteUsersService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [BitcoinAddressesUpdateComponent],
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
      .overrideTemplate(BitcoinAddressesUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(BitcoinAddressesUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    bitcoinAddressesService = TestBed.inject(BitcoinAddressesService);
    siteUsersService = TestBed.inject(SiteUsersService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call siteUser query and add missing value', () => {
      const bitcoinAddresses: IBitcoinAddresses = { id: 456 };
      const siteUser: ISiteUsers = { id: 43231 };
      bitcoinAddresses.siteUser = siteUser;

      const siteUserCollection: ISiteUsers[] = [{ id: 82178 }];
      jest.spyOn(siteUsersService, 'query').mockReturnValue(of(new HttpResponse({ body: siteUserCollection })));
      const expectedCollection: ISiteUsers[] = [siteUser, ...siteUserCollection];
      jest.spyOn(siteUsersService, 'addSiteUsersToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ bitcoinAddresses });
      comp.ngOnInit();

      expect(siteUsersService.query).toHaveBeenCalled();
      expect(siteUsersService.addSiteUsersToCollectionIfMissing).toHaveBeenCalledWith(siteUserCollection, siteUser);
      expect(comp.siteUsersCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const bitcoinAddresses: IBitcoinAddresses = { id: 456 };
      const siteUser: ISiteUsers = { id: 50961 };
      bitcoinAddresses.siteUser = siteUser;

      activatedRoute.data = of({ bitcoinAddresses });
      comp.ngOnInit();

      expect(comp.editForm.value).toEqual(expect.objectContaining(bitcoinAddresses));
      expect(comp.siteUsersCollection).toContain(siteUser);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<BitcoinAddresses>>();
      const bitcoinAddresses = { id: 123 };
      jest.spyOn(bitcoinAddressesService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ bitcoinAddresses });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: bitcoinAddresses }));
      saveSubject.complete();

      // THEN
      expect(comp.previousState).toHaveBeenCalled();
      expect(bitcoinAddressesService.update).toHaveBeenCalledWith(bitcoinAddresses);
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<BitcoinAddresses>>();
      const bitcoinAddresses = new BitcoinAddresses();
      jest.spyOn(bitcoinAddressesService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ bitcoinAddresses });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: bitcoinAddresses }));
      saveSubject.complete();

      // THEN
      expect(bitcoinAddressesService.create).toHaveBeenCalledWith(bitcoinAddresses);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<BitcoinAddresses>>();
      const bitcoinAddresses = { id: 123 };
      jest.spyOn(bitcoinAddressesService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ bitcoinAddresses });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(bitcoinAddressesService.update).toHaveBeenCalledWith(bitcoinAddresses);
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
