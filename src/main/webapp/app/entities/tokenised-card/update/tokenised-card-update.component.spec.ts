import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { TokenisedCardService } from '../service/tokenised-card.service';
import { ITokenisedCard, TokenisedCard } from '../tokenised-card.model';

import { TokenisedCardUpdateComponent } from './tokenised-card-update.component';

describe('TokenisedCard Management Update Component', () => {
  let comp: TokenisedCardUpdateComponent;
  let fixture: ComponentFixture<TokenisedCardUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let tokenisedCardService: TokenisedCardService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [TokenisedCardUpdateComponent],
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
      .overrideTemplate(TokenisedCardUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(TokenisedCardUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    tokenisedCardService = TestBed.inject(TokenisedCardService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const tokenisedCard: ITokenisedCard = { id: 456 };

      activatedRoute.data = of({ tokenisedCard });
      comp.ngOnInit();

      expect(comp.editForm.value).toEqual(expect.objectContaining(tokenisedCard));
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<TokenisedCard>>();
      const tokenisedCard = { id: 123 };
      jest.spyOn(tokenisedCardService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ tokenisedCard });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: tokenisedCard }));
      saveSubject.complete();

      // THEN
      expect(comp.previousState).toHaveBeenCalled();
      expect(tokenisedCardService.update).toHaveBeenCalledWith(tokenisedCard);
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<TokenisedCard>>();
      const tokenisedCard = new TokenisedCard();
      jest.spyOn(tokenisedCardService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ tokenisedCard });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: tokenisedCard }));
      saveSubject.complete();

      // THEN
      expect(tokenisedCardService.create).toHaveBeenCalledWith(tokenisedCard);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<TokenisedCard>>();
      const tokenisedCard = { id: 123 };
      jest.spyOn(tokenisedCardService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ tokenisedCard });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(tokenisedCardService.update).toHaveBeenCalledWith(tokenisedCard);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
