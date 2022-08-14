import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { BankAccountsService } from '../service/bank-accounts.service';
import { IBankAccounts, BankAccounts } from '../bank-accounts.model';
import { ISiteUsers } from 'app/entities/site-users/site-users.model';
import { SiteUsersService } from 'app/entities/site-users/service/site-users.service';
import { ICurrencies } from 'app/entities/currencies/currencies.model';
import { CurrenciesService } from 'app/entities/currencies/service/currencies.service';

import { BankAccountsUpdateComponent } from './bank-accounts-update.component';

describe('BankAccounts Management Update Component', () => {
  let comp: BankAccountsUpdateComponent;
  let fixture: ComponentFixture<BankAccountsUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let bankAccountsService: BankAccountsService;
  let siteUsersService: SiteUsersService;
  let currenciesService: CurrenciesService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [BankAccountsUpdateComponent],
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
      .overrideTemplate(BankAccountsUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(BankAccountsUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    bankAccountsService = TestBed.inject(BankAccountsService);
    siteUsersService = TestBed.inject(SiteUsersService);
    currenciesService = TestBed.inject(CurrenciesService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call siteUser query and add missing value', () => {
      const bankAccounts: IBankAccounts = { id: 456 };
      const siteUser: ISiteUsers = { id: 63051 };
      bankAccounts.siteUser = siteUser;

      const siteUserCollection: ISiteUsers[] = [{ id: 13246 }];
      jest.spyOn(siteUsersService, 'query').mockReturnValue(of(new HttpResponse({ body: siteUserCollection })));
      const expectedCollection: ISiteUsers[] = [siteUser, ...siteUserCollection];
      jest.spyOn(siteUsersService, 'addSiteUsersToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ bankAccounts });
      comp.ngOnInit();

      expect(siteUsersService.query).toHaveBeenCalled();
      expect(siteUsersService.addSiteUsersToCollectionIfMissing).toHaveBeenCalledWith(siteUserCollection, siteUser);
      expect(comp.siteUsersCollection).toEqual(expectedCollection);
    });

    it('Should call currency query and add missing value', () => {
      const bankAccounts: IBankAccounts = { id: 456 };
      const currency: ICurrencies = { id: 28423 };
      bankAccounts.currency = currency;

      const currencyCollection: ICurrencies[] = [{ id: 42740 }];
      jest.spyOn(currenciesService, 'query').mockReturnValue(of(new HttpResponse({ body: currencyCollection })));
      const expectedCollection: ICurrencies[] = [currency, ...currencyCollection];
      jest.spyOn(currenciesService, 'addCurrenciesToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ bankAccounts });
      comp.ngOnInit();

      expect(currenciesService.query).toHaveBeenCalled();
      expect(currenciesService.addCurrenciesToCollectionIfMissing).toHaveBeenCalledWith(currencyCollection, currency);
      expect(comp.currenciesCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const bankAccounts: IBankAccounts = { id: 456 };
      const siteUser: ISiteUsers = { id: 47801 };
      bankAccounts.siteUser = siteUser;
      const currency: ICurrencies = { id: 37356 };
      bankAccounts.currency = currency;

      activatedRoute.data = of({ bankAccounts });
      comp.ngOnInit();

      expect(comp.editForm.value).toEqual(expect.objectContaining(bankAccounts));
      expect(comp.siteUsersCollection).toContain(siteUser);
      expect(comp.currenciesCollection).toContain(currency);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<BankAccounts>>();
      const bankAccounts = { id: 123 };
      jest.spyOn(bankAccountsService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ bankAccounts });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: bankAccounts }));
      saveSubject.complete();

      // THEN
      expect(comp.previousState).toHaveBeenCalled();
      expect(bankAccountsService.update).toHaveBeenCalledWith(bankAccounts);
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<BankAccounts>>();
      const bankAccounts = new BankAccounts();
      jest.spyOn(bankAccountsService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ bankAccounts });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: bankAccounts }));
      saveSubject.complete();

      // THEN
      expect(bankAccountsService.create).toHaveBeenCalledWith(bankAccounts);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<BankAccounts>>();
      const bankAccounts = { id: 123 };
      jest.spyOn(bankAccountsService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ bankAccounts });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(bankAccountsService.update).toHaveBeenCalledWith(bankAccounts);
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
