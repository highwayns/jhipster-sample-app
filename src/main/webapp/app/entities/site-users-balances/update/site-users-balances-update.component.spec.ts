import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { SiteUsersBalancesService } from '../service/site-users-balances.service';
import { ISiteUsersBalances, SiteUsersBalances } from '../site-users-balances.model';
import { ISiteUsers } from 'app/entities/site-users/site-users.model';
import { SiteUsersService } from 'app/entities/site-users/service/site-users.service';
import { ICurrencies } from 'app/entities/currencies/currencies.model';
import { CurrenciesService } from 'app/entities/currencies/service/currencies.service';

import { SiteUsersBalancesUpdateComponent } from './site-users-balances-update.component';

describe('SiteUsersBalances Management Update Component', () => {
  let comp: SiteUsersBalancesUpdateComponent;
  let fixture: ComponentFixture<SiteUsersBalancesUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let siteUsersBalancesService: SiteUsersBalancesService;
  let siteUsersService: SiteUsersService;
  let currenciesService: CurrenciesService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [SiteUsersBalancesUpdateComponent],
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
      .overrideTemplate(SiteUsersBalancesUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(SiteUsersBalancesUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    siteUsersBalancesService = TestBed.inject(SiteUsersBalancesService);
    siteUsersService = TestBed.inject(SiteUsersService);
    currenciesService = TestBed.inject(CurrenciesService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call siteUser query and add missing value', () => {
      const siteUsersBalances: ISiteUsersBalances = { id: 456 };
      const siteUser: ISiteUsers = { id: 8850 };
      siteUsersBalances.siteUser = siteUser;

      const siteUserCollection: ISiteUsers[] = [{ id: 79111 }];
      jest.spyOn(siteUsersService, 'query').mockReturnValue(of(new HttpResponse({ body: siteUserCollection })));
      const expectedCollection: ISiteUsers[] = [siteUser, ...siteUserCollection];
      jest.spyOn(siteUsersService, 'addSiteUsersToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ siteUsersBalances });
      comp.ngOnInit();

      expect(siteUsersService.query).toHaveBeenCalled();
      expect(siteUsersService.addSiteUsersToCollectionIfMissing).toHaveBeenCalledWith(siteUserCollection, siteUser);
      expect(comp.siteUsersCollection).toEqual(expectedCollection);
    });

    it('Should call currency query and add missing value', () => {
      const siteUsersBalances: ISiteUsersBalances = { id: 456 };
      const currency: ICurrencies = { id: 52065 };
      siteUsersBalances.currency = currency;

      const currencyCollection: ICurrencies[] = [{ id: 17539 }];
      jest.spyOn(currenciesService, 'query').mockReturnValue(of(new HttpResponse({ body: currencyCollection })));
      const expectedCollection: ICurrencies[] = [currency, ...currencyCollection];
      jest.spyOn(currenciesService, 'addCurrenciesToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ siteUsersBalances });
      comp.ngOnInit();

      expect(currenciesService.query).toHaveBeenCalled();
      expect(currenciesService.addCurrenciesToCollectionIfMissing).toHaveBeenCalledWith(currencyCollection, currency);
      expect(comp.currenciesCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const siteUsersBalances: ISiteUsersBalances = { id: 456 };
      const siteUser: ISiteUsers = { id: 5254 };
      siteUsersBalances.siteUser = siteUser;
      const currency: ICurrencies = { id: 50988 };
      siteUsersBalances.currency = currency;

      activatedRoute.data = of({ siteUsersBalances });
      comp.ngOnInit();

      expect(comp.editForm.value).toEqual(expect.objectContaining(siteUsersBalances));
      expect(comp.siteUsersCollection).toContain(siteUser);
      expect(comp.currenciesCollection).toContain(currency);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<SiteUsersBalances>>();
      const siteUsersBalances = { id: 123 };
      jest.spyOn(siteUsersBalancesService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ siteUsersBalances });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: siteUsersBalances }));
      saveSubject.complete();

      // THEN
      expect(comp.previousState).toHaveBeenCalled();
      expect(siteUsersBalancesService.update).toHaveBeenCalledWith(siteUsersBalances);
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<SiteUsersBalances>>();
      const siteUsersBalances = new SiteUsersBalances();
      jest.spyOn(siteUsersBalancesService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ siteUsersBalances });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: siteUsersBalances }));
      saveSubject.complete();

      // THEN
      expect(siteUsersBalancesService.create).toHaveBeenCalledWith(siteUsersBalances);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<SiteUsersBalances>>();
      const siteUsersBalances = { id: 123 };
      jest.spyOn(siteUsersBalancesService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ siteUsersBalances });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(siteUsersBalancesService.update).toHaveBeenCalledWith(siteUsersBalances);
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

    describe('trackCurrenciesById', () => {
      it('Should return tracked Currencies primary key', () => {
        const entity = { id: 123 };
        const trackResult = comp.trackCurrenciesById(0, entity);
        expect(trackResult).toEqual(entity.id);
      });
    });
  });
});
