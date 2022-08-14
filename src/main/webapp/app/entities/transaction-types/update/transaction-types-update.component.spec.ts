import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { TransactionTypesService } from '../service/transaction-types.service';
import { ITransactionTypes, TransactionTypes } from '../transaction-types.model';

import { TransactionTypesUpdateComponent } from './transaction-types-update.component';

describe('TransactionTypes Management Update Component', () => {
  let comp: TransactionTypesUpdateComponent;
  let fixture: ComponentFixture<TransactionTypesUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let transactionTypesService: TransactionTypesService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [TransactionTypesUpdateComponent],
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
      .overrideTemplate(TransactionTypesUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(TransactionTypesUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    transactionTypesService = TestBed.inject(TransactionTypesService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const transactionTypes: ITransactionTypes = { id: 456 };

      activatedRoute.data = of({ transactionTypes });
      comp.ngOnInit();

      expect(comp.editForm.value).toEqual(expect.objectContaining(transactionTypes));
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<TransactionTypes>>();
      const transactionTypes = { id: 123 };
      jest.spyOn(transactionTypesService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ transactionTypes });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: transactionTypes }));
      saveSubject.complete();

      // THEN
      expect(comp.previousState).toHaveBeenCalled();
      expect(transactionTypesService.update).toHaveBeenCalledWith(transactionTypes);
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<TransactionTypes>>();
      const transactionTypes = new TransactionTypes();
      jest.spyOn(transactionTypesService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ transactionTypes });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: transactionTypes }));
      saveSubject.complete();

      // THEN
      expect(transactionTypesService.create).toHaveBeenCalledWith(transactionTypes);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<TransactionTypes>>();
      const transactionTypes = { id: 123 };
      jest.spyOn(transactionTypesService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ transactionTypes });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(transactionTypesService.update).toHaveBeenCalledWith(transactionTypes);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
