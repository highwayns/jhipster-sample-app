import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { SiteUsersService } from '../service/site-users.service';
import { ISiteUsers, SiteUsers } from '../site-users.model';
import { IIsoCountries } from 'app/entities/iso-countries/iso-countries.model';
import { IsoCountriesService } from 'app/entities/iso-countries/service/iso-countries.service';
import { IFeeSchedule } from 'app/entities/fee-schedule/fee-schedule.model';
import { FeeScheduleService } from 'app/entities/fee-schedule/service/fee-schedule.service';
import { ICurrencies } from 'app/entities/currencies/currencies.model';
import { CurrenciesService } from 'app/entities/currencies/service/currencies.service';

import { SiteUsersUpdateComponent } from './site-users-update.component';

describe('SiteUsers Management Update Component', () => {
  let comp: SiteUsersUpdateComponent;
  let fixture: ComponentFixture<SiteUsersUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let siteUsersService: SiteUsersService;
  let isoCountriesService: IsoCountriesService;
  let feeScheduleService: FeeScheduleService;
  let currenciesService: CurrenciesService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [SiteUsersUpdateComponent],
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
      .overrideTemplate(SiteUsersUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(SiteUsersUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    siteUsersService = TestBed.inject(SiteUsersService);
    isoCountriesService = TestBed.inject(IsoCountriesService);
    feeScheduleService = TestBed.inject(FeeScheduleService);
    currenciesService = TestBed.inject(CurrenciesService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call country query and add missing value', () => {
      const siteUsers: ISiteUsers = { id: 456 };
      const country: IIsoCountries = { id: 49347 };
      siteUsers.country = country;

      const countryCollection: IIsoCountries[] = [{ id: 39548 }];
      jest.spyOn(isoCountriesService, 'query').mockReturnValue(of(new HttpResponse({ body: countryCollection })));
      const expectedCollection: IIsoCountries[] = [country, ...countryCollection];
      jest.spyOn(isoCountriesService, 'addIsoCountriesToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ siteUsers });
      comp.ngOnInit();

      expect(isoCountriesService.query).toHaveBeenCalled();
      expect(isoCountriesService.addIsoCountriesToCollectionIfMissing).toHaveBeenCalledWith(countryCollection, country);
      expect(comp.countriesCollection).toEqual(expectedCollection);
    });

    it('Should call feeSchedule query and add missing value', () => {
      const siteUsers: ISiteUsers = { id: 456 };
      const feeSchedule: IFeeSchedule = { id: 37219 };
      siteUsers.feeSchedule = feeSchedule;

      const feeScheduleCollection: IFeeSchedule[] = [{ id: 75735 }];
      jest.spyOn(feeScheduleService, 'query').mockReturnValue(of(new HttpResponse({ body: feeScheduleCollection })));
      const expectedCollection: IFeeSchedule[] = [feeSchedule, ...feeScheduleCollection];
      jest.spyOn(feeScheduleService, 'addFeeScheduleToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ siteUsers });
      comp.ngOnInit();

      expect(feeScheduleService.query).toHaveBeenCalled();
      expect(feeScheduleService.addFeeScheduleToCollectionIfMissing).toHaveBeenCalledWith(feeScheduleCollection, feeSchedule);
      expect(comp.feeSchedulesCollection).toEqual(expectedCollection);
    });

    it('Should call defaultCurrency query and add missing value', () => {
      const siteUsers: ISiteUsers = { id: 456 };
      const defaultCurrency: ICurrencies = { id: 78866 };
      siteUsers.defaultCurrency = defaultCurrency;

      const defaultCurrencyCollection: ICurrencies[] = [{ id: 77863 }];
      jest.spyOn(currenciesService, 'query').mockReturnValue(of(new HttpResponse({ body: defaultCurrencyCollection })));
      const expectedCollection: ICurrencies[] = [defaultCurrency, ...defaultCurrencyCollection];
      jest.spyOn(currenciesService, 'addCurrenciesToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ siteUsers });
      comp.ngOnInit();

      expect(currenciesService.query).toHaveBeenCalled();
      expect(currenciesService.addCurrenciesToCollectionIfMissing).toHaveBeenCalledWith(defaultCurrencyCollection, defaultCurrency);
      expect(comp.defaultCurrenciesCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const siteUsers: ISiteUsers = { id: 456 };
      const country: IIsoCountries = { id: 71949 };
      siteUsers.country = country;
      const feeSchedule: IFeeSchedule = { id: 12980 };
      siteUsers.feeSchedule = feeSchedule;
      const defaultCurrency: ICurrencies = { id: 27947 };
      siteUsers.defaultCurrency = defaultCurrency;

      activatedRoute.data = of({ siteUsers });
      comp.ngOnInit();

      expect(comp.editForm.value).toEqual(expect.objectContaining(siteUsers));
      expect(comp.countriesCollection).toContain(country);
      expect(comp.feeSchedulesCollection).toContain(feeSchedule);
      expect(comp.defaultCurrenciesCollection).toContain(defaultCurrency);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<SiteUsers>>();
      const siteUsers = { id: 123 };
      jest.spyOn(siteUsersService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ siteUsers });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: siteUsers }));
      saveSubject.complete();

      // THEN
      expect(comp.previousState).toHaveBeenCalled();
      expect(siteUsersService.update).toHaveBeenCalledWith(siteUsers);
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<SiteUsers>>();
      const siteUsers = new SiteUsers();
      jest.spyOn(siteUsersService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ siteUsers });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: siteUsers }));
      saveSubject.complete();

      // THEN
      expect(siteUsersService.create).toHaveBeenCalledWith(siteUsers);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<SiteUsers>>();
      const siteUsers = { id: 123 };
      jest.spyOn(siteUsersService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ siteUsers });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(siteUsersService.update).toHaveBeenCalledWith(siteUsers);
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

    describe('trackFeeScheduleById', () => {
      it('Should return tracked FeeSchedule primary key', () => {
        const entity = { id: 123 };
        const trackResult = comp.trackFeeScheduleById(0, entity);
        expect(trackResult).toEqual(entity.id);
      });
    });

    describe('trackCurrenciesById', () => {
      it('Should return tracked Currencies primary key', () => {
        const entity = { id: 123 };
        const trackResult = comp.trackCurrenciesById(0, entity);
        expect(trackResult).toEqual(entity.id);
      });
    });
  });
});
