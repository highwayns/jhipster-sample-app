import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { HistoricalDataService } from '../service/historical-data.service';
import { IHistoricalData, HistoricalData } from '../historical-data.model';

import { HistoricalDataUpdateComponent } from './historical-data-update.component';

describe('HistoricalData Management Update Component', () => {
  let comp: HistoricalDataUpdateComponent;
  let fixture: ComponentFixture<HistoricalDataUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let historicalDataService: HistoricalDataService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [HistoricalDataUpdateComponent],
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
      .overrideTemplate(HistoricalDataUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(HistoricalDataUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    historicalDataService = TestBed.inject(HistoricalDataService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const historicalData: IHistoricalData = { id: 456 };

      activatedRoute.data = of({ historicalData });
      comp.ngOnInit();

      expect(comp.editForm.value).toEqual(expect.objectContaining(historicalData));
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<HistoricalData>>();
      const historicalData = { id: 123 };
      jest.spyOn(historicalDataService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ historicalData });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: historicalData }));
      saveSubject.complete();

      // THEN
      expect(comp.previousState).toHaveBeenCalled();
      expect(historicalDataService.update).toHaveBeenCalledWith(historicalData);
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<HistoricalData>>();
      const historicalData = new HistoricalData();
      jest.spyOn(historicalDataService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ historicalData });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: historicalData }));
      saveSubject.complete();

      // THEN
      expect(historicalDataService.create).toHaveBeenCalledWith(historicalData);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<HistoricalData>>();
      const historicalData = { id: 123 };
      jest.spyOn(historicalDataService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ historicalData });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(historicalDataService.update).toHaveBeenCalledWith(historicalData);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
