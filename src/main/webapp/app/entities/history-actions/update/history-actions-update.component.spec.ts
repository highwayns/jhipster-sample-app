import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { HistoryActionsService } from '../service/history-actions.service';
import { IHistoryActions, HistoryActions } from '../history-actions.model';

import { HistoryActionsUpdateComponent } from './history-actions-update.component';

describe('HistoryActions Management Update Component', () => {
  let comp: HistoryActionsUpdateComponent;
  let fixture: ComponentFixture<HistoryActionsUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let historyActionsService: HistoryActionsService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [HistoryActionsUpdateComponent],
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
      .overrideTemplate(HistoryActionsUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(HistoryActionsUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    historyActionsService = TestBed.inject(HistoryActionsService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const historyActions: IHistoryActions = { id: 456 };

      activatedRoute.data = of({ historyActions });
      comp.ngOnInit();

      expect(comp.editForm.value).toEqual(expect.objectContaining(historyActions));
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<HistoryActions>>();
      const historyActions = { id: 123 };
      jest.spyOn(historyActionsService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ historyActions });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: historyActions }));
      saveSubject.complete();

      // THEN
      expect(comp.previousState).toHaveBeenCalled();
      expect(historyActionsService.update).toHaveBeenCalledWith(historyActions);
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<HistoryActions>>();
      const historyActions = new HistoryActions();
      jest.spyOn(historyActionsService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ historyActions });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: historyActions }));
      saveSubject.complete();

      // THEN
      expect(historyActionsService.create).toHaveBeenCalledWith(historyActions);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<HistoryActions>>();
      const historyActions = { id: 123 };
      jest.spyOn(historyActionsService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ historyActions });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(historyActionsService.update).toHaveBeenCalledWith(historyActions);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
