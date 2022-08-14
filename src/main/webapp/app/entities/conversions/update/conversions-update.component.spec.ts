import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { ConversionsService } from '../service/conversions.service';
import { IConversions, Conversions } from '../conversions.model';
import { ICurrencies } from 'app/entities/currencies/currencies.model';
import { CurrenciesService } from 'app/entities/currencies/service/currencies.service';

import { ConversionsUpdateComponent } from './conversions-update.component';

describe('Conversions Management Update Component', () => {
  let comp: ConversionsUpdateComponent;
  let fixture: ComponentFixture<ConversionsUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let conversionsService: ConversionsService;
  let currenciesService: CurrenciesService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [ConversionsUpdateComponent],
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
      .overrideTemplate(ConversionsUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(ConversionsUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    conversionsService = TestBed.inject(ConversionsService);
    currenciesService = TestBed.inject(CurrenciesService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call currency query and add missing value', () => {
      const conversions: IConversions = { id: 456 };
      const currency: ICurrencies = { id: 73464 };
      conversions.currency = currency;

      const currencyCollection: ICurrencies[] = [{ id: 88593 }];
      jest.spyOn(currenciesService, 'query').mockReturnValue(of(new HttpResponse({ body: currencyCollection })));
      const expectedCollection: ICurrencies[] = [currency, ...currencyCollection];
      jest.spyOn(currenciesService, 'addCurrenciesToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ conversions });
      comp.ngOnInit();

      expect(currenciesService.query).toHaveBeenCalled();
      expect(currenciesService.addCurrenciesToCollectionIfMissing).toHaveBeenCalledWith(currencyCollection, currency);
      expect(comp.currenciesCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const conversions: IConversions = { id: 456 };
      const currency: ICurrencies = { id: 328 };
      conversions.currency = currency;

      activatedRoute.data = of({ conversions });
      comp.ngOnInit();

      expect(comp.editForm.value).toEqual(expect.objectContaining(conversions));
      expect(comp.currenciesCollection).toContain(currency);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Conversions>>();
      const conversions = { id: 123 };
      jest.spyOn(conversionsService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ conversions });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: conversions }));
      saveSubject.complete();

      // THEN
      expect(comp.previousState).toHaveBeenCalled();
      expect(conversionsService.update).toHaveBeenCalledWith(conversions);
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Conversions>>();
      const conversions = new Conversions();
      jest.spyOn(conversionsService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ conversions });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: conversions }));
      saveSubject.complete();

      // THEN
      expect(conversionsService.create).toHaveBeenCalledWith(conversions);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Conversions>>();
      const conversions = { id: 123 };
      jest.spyOn(conversionsService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ conversions });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(conversionsService.update).toHaveBeenCalledWith(conversions);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Tracking relationships identifiers', () => {
    describe('trackCurrenciesById', () => {
      it('Should return tracked Currencies primary key', () => {
        const entity = { id: 123 };
        const trackResult = comp.trackCurrenciesById(0, entity);
        expect(trackResult).toEqual(entity.id);
      });
    });
  });
});
