import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { StatusEscrowsService } from '../service/status-escrows.service';
import { IStatusEscrows, StatusEscrows } from '../status-escrows.model';
import { ICurrencies } from 'app/entities/currencies/currencies.model';
import { CurrenciesService } from 'app/entities/currencies/service/currencies.service';
import { IStatus } from 'app/entities/status/status.model';
import { StatusService } from 'app/entities/status/service/status.service';

import { StatusEscrowsUpdateComponent } from './status-escrows-update.component';

describe('StatusEscrows Management Update Component', () => {
  let comp: StatusEscrowsUpdateComponent;
  let fixture: ComponentFixture<StatusEscrowsUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let statusEscrowsService: StatusEscrowsService;
  let currenciesService: CurrenciesService;
  let statusService: StatusService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [StatusEscrowsUpdateComponent],
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
      .overrideTemplate(StatusEscrowsUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(StatusEscrowsUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    statusEscrowsService = TestBed.inject(StatusEscrowsService);
    currenciesService = TestBed.inject(CurrenciesService);
    statusService = TestBed.inject(StatusService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call currency query and add missing value', () => {
      const statusEscrows: IStatusEscrows = { id: 456 };
      const currency: ICurrencies = { id: 29399 };
      statusEscrows.currency = currency;

      const currencyCollection: ICurrencies[] = [{ id: 75070 }];
      jest.spyOn(currenciesService, 'query').mockReturnValue(of(new HttpResponse({ body: currencyCollection })));
      const expectedCollection: ICurrencies[] = [currency, ...currencyCollection];
      jest.spyOn(currenciesService, 'addCurrenciesToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ statusEscrows });
      comp.ngOnInit();

      expect(currenciesService.query).toHaveBeenCalled();
      expect(currenciesService.addCurrenciesToCollectionIfMissing).toHaveBeenCalledWith(currencyCollection, currency);
      expect(comp.currenciesCollection).toEqual(expectedCollection);
    });

    it('Should call statusId query and add missing value', () => {
      const statusEscrows: IStatusEscrows = { id: 456 };
      const statusId: IStatus = { id: 62581 };
      statusEscrows.statusId = statusId;

      const statusIdCollection: IStatus[] = [{ id: 50589 }];
      jest.spyOn(statusService, 'query').mockReturnValue(of(new HttpResponse({ body: statusIdCollection })));
      const expectedCollection: IStatus[] = [statusId, ...statusIdCollection];
      jest.spyOn(statusService, 'addStatusToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ statusEscrows });
      comp.ngOnInit();

      expect(statusService.query).toHaveBeenCalled();
      expect(statusService.addStatusToCollectionIfMissing).toHaveBeenCalledWith(statusIdCollection, statusId);
      expect(comp.statusIdsCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const statusEscrows: IStatusEscrows = { id: 456 };
      const currency: ICurrencies = { id: 13747 };
      statusEscrows.currency = currency;
      const statusId: IStatus = { id: 3707 };
      statusEscrows.statusId = statusId;

      activatedRoute.data = of({ statusEscrows });
      comp.ngOnInit();

      expect(comp.editForm.value).toEqual(expect.objectContaining(statusEscrows));
      expect(comp.currenciesCollection).toContain(currency);
      expect(comp.statusIdsCollection).toContain(statusId);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<StatusEscrows>>();
      const statusEscrows = { id: 123 };
      jest.spyOn(statusEscrowsService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ statusEscrows });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: statusEscrows }));
      saveSubject.complete();

      // THEN
      expect(comp.previousState).toHaveBeenCalled();
      expect(statusEscrowsService.update).toHaveBeenCalledWith(statusEscrows);
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<StatusEscrows>>();
      const statusEscrows = new StatusEscrows();
      jest.spyOn(statusEscrowsService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ statusEscrows });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: statusEscrows }));
      saveSubject.complete();

      // THEN
      expect(statusEscrowsService.create).toHaveBeenCalledWith(statusEscrows);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<StatusEscrows>>();
      const statusEscrows = { id: 123 };
      jest.spyOn(statusEscrowsService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ statusEscrows });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(statusEscrowsService.update).toHaveBeenCalledWith(statusEscrows);
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

    describe('trackStatusById', () => {
      it('Should return tracked Status primary key', () => {
        const entity = { id: 123 };
        const trackResult = comp.trackStatusById(0, entity);
        expect(trackResult).toEqual(entity.id);
      });
    });
  });
});
