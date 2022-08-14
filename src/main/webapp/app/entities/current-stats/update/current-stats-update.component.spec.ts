import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { CurrentStatsService } from '../service/current-stats.service';
import { ICurrentStats, CurrentStats } from '../current-stats.model';

import { CurrentStatsUpdateComponent } from './current-stats-update.component';

describe('CurrentStats Management Update Component', () => {
  let comp: CurrentStatsUpdateComponent;
  let fixture: ComponentFixture<CurrentStatsUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let currentStatsService: CurrentStatsService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [CurrentStatsUpdateComponent],
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
      .overrideTemplate(CurrentStatsUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(CurrentStatsUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    currentStatsService = TestBed.inject(CurrentStatsService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const currentStats: ICurrentStats = { id: 456 };

      activatedRoute.data = of({ currentStats });
      comp.ngOnInit();

      expect(comp.editForm.value).toEqual(expect.objectContaining(currentStats));
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<CurrentStats>>();
      const currentStats = { id: 123 };
      jest.spyOn(currentStatsService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ currentStats });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: currentStats }));
      saveSubject.complete();

      // THEN
      expect(comp.previousState).toHaveBeenCalled();
      expect(currentStatsService.update).toHaveBeenCalledWith(currentStats);
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<CurrentStats>>();
      const currentStats = new CurrentStats();
      jest.spyOn(currentStatsService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ currentStats });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: currentStats }));
      saveSubject.complete();

      // THEN
      expect(currentStatsService.create).toHaveBeenCalledWith(currentStats);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<CurrentStats>>();
      const currentStats = { id: 123 };
      jest.spyOn(currentStatsService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ currentStats });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(currentStatsService.update).toHaveBeenCalledWith(currentStats);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
