import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { CardTokenDataService } from '../service/card-token-data.service';
import { ICardTokenData, CardTokenData } from '../card-token-data.model';

import { CardTokenDataUpdateComponent } from './card-token-data-update.component';

describe('CardTokenData Management Update Component', () => {
  let comp: CardTokenDataUpdateComponent;
  let fixture: ComponentFixture<CardTokenDataUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let cardTokenDataService: CardTokenDataService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [CardTokenDataUpdateComponent],
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
      .overrideTemplate(CardTokenDataUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(CardTokenDataUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    cardTokenDataService = TestBed.inject(CardTokenDataService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const cardTokenData: ICardTokenData = { id: 456 };

      activatedRoute.data = of({ cardTokenData });
      comp.ngOnInit();

      expect(comp.editForm.value).toEqual(expect.objectContaining(cardTokenData));
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<CardTokenData>>();
      const cardTokenData = { id: 123 };
      jest.spyOn(cardTokenDataService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ cardTokenData });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: cardTokenData }));
      saveSubject.complete();

      // THEN
      expect(comp.previousState).toHaveBeenCalled();
      expect(cardTokenDataService.update).toHaveBeenCalledWith(cardTokenData);
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<CardTokenData>>();
      const cardTokenData = new CardTokenData();
      jest.spyOn(cardTokenDataService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ cardTokenData });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: cardTokenData }));
      saveSubject.complete();

      // THEN
      expect(cardTokenDataService.create).toHaveBeenCalledWith(cardTokenData);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<CardTokenData>>();
      const cardTokenData = { id: 123 };
      jest.spyOn(cardTokenDataService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ cardTokenData });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(cardTokenDataService.update).toHaveBeenCalledWith(cardTokenData);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
