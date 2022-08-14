import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { TransactionsService } from '../service/transactions.service';
import { ITransactions, Transactions } from '../transactions.model';
import { ISiteUsers } from 'app/entities/site-users/site-users.model';
import { SiteUsersService } from 'app/entities/site-users/service/site-users.service';
import { ITransactionTypes } from 'app/entities/transaction-types/transaction-types.model';
import { TransactionTypesService } from 'app/entities/transaction-types/service/transaction-types.service';
import { ICurrencies } from 'app/entities/currencies/currencies.model';
import { CurrenciesService } from 'app/entities/currencies/service/currencies.service';

import { TransactionsUpdateComponent } from './transactions-update.component';

describe('Transactions Management Update Component', () => {
  let comp: TransactionsUpdateComponent;
  let fixture: ComponentFixture<TransactionsUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let transactionsService: TransactionsService;
  let siteUsersService: SiteUsersService;
  let transactionTypesService: TransactionTypesService;
  let currenciesService: CurrenciesService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [TransactionsUpdateComponent],
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
      .overrideTemplate(TransactionsUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(TransactionsUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    transactionsService = TestBed.inject(TransactionsService);
    siteUsersService = TestBed.inject(SiteUsersService);
    transactionTypesService = TestBed.inject(TransactionTypesService);
    currenciesService = TestBed.inject(CurrenciesService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call siteUser query and add missing value', () => {
      const transactions: ITransactions = { id: 456 };
      const siteUser: ISiteUsers = { id: 56327 };
      transactions.siteUser = siteUser;

      const siteUserCollection: ISiteUsers[] = [{ id: 1322 }];
      jest.spyOn(siteUsersService, 'query').mockReturnValue(of(new HttpResponse({ body: siteUserCollection })));
      const expectedCollection: ISiteUsers[] = [siteUser, ...siteUserCollection];
      jest.spyOn(siteUsersService, 'addSiteUsersToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ transactions });
      comp.ngOnInit();

      expect(siteUsersService.query).toHaveBeenCalled();
      expect(siteUsersService.addSiteUsersToCollectionIfMissing).toHaveBeenCalledWith(siteUserCollection, siteUser);
      expect(comp.siteUsersCollection).toEqual(expectedCollection);
    });

    it('Should call siteUser1 query and add missing value', () => {
      const transactions: ITransactions = { id: 456 };
      const siteUser1: ISiteUsers = { id: 34833 };
      transactions.siteUser1 = siteUser1;

      const siteUser1Collection: ISiteUsers[] = [{ id: 4815 }];
      jest.spyOn(siteUsersService, 'query').mockReturnValue(of(new HttpResponse({ body: siteUser1Collection })));
      const expectedCollection: ISiteUsers[] = [siteUser1, ...siteUser1Collection];
      jest.spyOn(siteUsersService, 'addSiteUsersToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ transactions });
      comp.ngOnInit();

      expect(siteUsersService.query).toHaveBeenCalled();
      expect(siteUsersService.addSiteUsersToCollectionIfMissing).toHaveBeenCalledWith(siteUser1Collection, siteUser1);
      expect(comp.siteUser1sCollection).toEqual(expectedCollection);
    });

    it('Should call transactionType query and add missing value', () => {
      const transactions: ITransactions = { id: 456 };
      const transactionType: ITransactionTypes = { id: 5114 };
      transactions.transactionType = transactionType;

      const transactionTypeCollection: ITransactionTypes[] = [{ id: 46212 }];
      jest.spyOn(transactionTypesService, 'query').mockReturnValue(of(new HttpResponse({ body: transactionTypeCollection })));
      const expectedCollection: ITransactionTypes[] = [transactionType, ...transactionTypeCollection];
      jest.spyOn(transactionTypesService, 'addTransactionTypesToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ transactions });
      comp.ngOnInit();

      expect(transactionTypesService.query).toHaveBeenCalled();
      expect(transactionTypesService.addTransactionTypesToCollectionIfMissing).toHaveBeenCalledWith(
        transactionTypeCollection,
        transactionType
      );
      expect(comp.transactionTypesCollection).toEqual(expectedCollection);
    });

    it('Should call transactionType1 query and add missing value', () => {
      const transactions: ITransactions = { id: 456 };
      const transactionType1: ITransactionTypes = { id: 94248 };
      transactions.transactionType1 = transactionType1;

      const transactionType1Collection: ITransactionTypes[] = [{ id: 23640 }];
      jest.spyOn(transactionTypesService, 'query').mockReturnValue(of(new HttpResponse({ body: transactionType1Collection })));
      const expectedCollection: ITransactionTypes[] = [transactionType1, ...transactionType1Collection];
      jest.spyOn(transactionTypesService, 'addTransactionTypesToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ transactions });
      comp.ngOnInit();

      expect(transactionTypesService.query).toHaveBeenCalled();
      expect(transactionTypesService.addTransactionTypesToCollectionIfMissing).toHaveBeenCalledWith(
        transactionType1Collection,
        transactionType1
      );
      expect(comp.transactionType1sCollection).toEqual(expectedCollection);
    });

    it('Should call currency1 query and add missing value', () => {
      const transactions: ITransactions = { id: 456 };
      const currency1: ICurrencies = { id: 84487 };
      transactions.currency1 = currency1;

      const currency1Collection: ICurrencies[] = [{ id: 22411 }];
      jest.spyOn(currenciesService, 'query').mockReturnValue(of(new HttpResponse({ body: currency1Collection })));
      const expectedCollection: ICurrencies[] = [currency1, ...currency1Collection];
      jest.spyOn(currenciesService, 'addCurrenciesToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ transactions });
      comp.ngOnInit();

      expect(currenciesService.query).toHaveBeenCalled();
      expect(currenciesService.addCurrenciesToCollectionIfMissing).toHaveBeenCalledWith(currency1Collection, currency1);
      expect(comp.currency1sCollection).toEqual(expectedCollection);
    });

    it('Should call convertFromCurrency query and add missing value', () => {
      const transactions: ITransactions = { id: 456 };
      const convertFromCurrency: ICurrencies = { id: 66992 };
      transactions.convertFromCurrency = convertFromCurrency;

      const convertFromCurrencyCollection: ICurrencies[] = [{ id: 41414 }];
      jest.spyOn(currenciesService, 'query').mockReturnValue(of(new HttpResponse({ body: convertFromCurrencyCollection })));
      const expectedCollection: ICurrencies[] = [convertFromCurrency, ...convertFromCurrencyCollection];
      jest.spyOn(currenciesService, 'addCurrenciesToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ transactions });
      comp.ngOnInit();

      expect(currenciesService.query).toHaveBeenCalled();
      expect(currenciesService.addCurrenciesToCollectionIfMissing).toHaveBeenCalledWith(convertFromCurrencyCollection, convertFromCurrency);
      expect(comp.convertFromCurrenciesCollection).toEqual(expectedCollection);
    });

    it('Should call convertToCurrency query and add missing value', () => {
      const transactions: ITransactions = { id: 456 };
      const convertToCurrency: ICurrencies = { id: 79451 };
      transactions.convertToCurrency = convertToCurrency;

      const convertToCurrencyCollection: ICurrencies[] = [{ id: 27749 }];
      jest.spyOn(currenciesService, 'query').mockReturnValue(of(new HttpResponse({ body: convertToCurrencyCollection })));
      const expectedCollection: ICurrencies[] = [convertToCurrency, ...convertToCurrencyCollection];
      jest.spyOn(currenciesService, 'addCurrenciesToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ transactions });
      comp.ngOnInit();

      expect(currenciesService.query).toHaveBeenCalled();
      expect(currenciesService.addCurrenciesToCollectionIfMissing).toHaveBeenCalledWith(convertToCurrencyCollection, convertToCurrency);
      expect(comp.convertToCurrenciesCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const transactions: ITransactions = { id: 456 };
      const siteUser: ISiteUsers = { id: 42242 };
      transactions.siteUser = siteUser;
      const siteUser1: ISiteUsers = { id: 43315 };
      transactions.siteUser1 = siteUser1;
      const transactionType: ITransactionTypes = { id: 22116 };
      transactions.transactionType = transactionType;
      const transactionType1: ITransactionTypes = { id: 38451 };
      transactions.transactionType1 = transactionType1;
      const currency1: ICurrencies = { id: 79473 };
      transactions.currency1 = currency1;
      const convertFromCurrency: ICurrencies = { id: 26353 };
      transactions.convertFromCurrency = convertFromCurrency;
      const convertToCurrency: ICurrencies = { id: 24592 };
      transactions.convertToCurrency = convertToCurrency;

      activatedRoute.data = of({ transactions });
      comp.ngOnInit();

      expect(comp.editForm.value).toEqual(expect.objectContaining(transactions));
      expect(comp.siteUsersCollection).toContain(siteUser);
      expect(comp.siteUser1sCollection).toContain(siteUser1);
      expect(comp.transactionTypesCollection).toContain(transactionType);
      expect(comp.transactionType1sCollection).toContain(transactionType1);
      expect(comp.currency1sCollection).toContain(currency1);
      expect(comp.convertFromCurrenciesCollection).toContain(convertFromCurrency);
      expect(comp.convertToCurrenciesCollection).toContain(convertToCurrency);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Transactions>>();
      const transactions = { id: 123 };
      jest.spyOn(transactionsService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ transactions });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: transactions }));
      saveSubject.complete();

      // THEN
      expect(comp.previousState).toHaveBeenCalled();
      expect(transactionsService.update).toHaveBeenCalledWith(transactions);
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Transactions>>();
      const transactions = new Transactions();
      jest.spyOn(transactionsService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ transactions });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: transactions }));
      saveSubject.complete();

      // THEN
      expect(transactionsService.create).toHaveBeenCalledWith(transactions);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Transactions>>();
      const transactions = { id: 123 };
      jest.spyOn(transactionsService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ transactions });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(transactionsService.update).toHaveBeenCalledWith(transactions);
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

    describe('trackTransactionTypesById', () => {
      it('Should return tracked TransactionTypes primary key', () => {
        const entity = { id: 123 };
        const trackResult = comp.trackTransactionTypesById(0, entity);
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
