import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { TokenisedCardDetailComponent } from './tokenised-card-detail.component';

describe('TokenisedCard Management Detail Component', () => {
  let comp: TokenisedCardDetailComponent;
  let fixture: ComponentFixture<TokenisedCardDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [TokenisedCardDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ tokenisedCard: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(TokenisedCardDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(TokenisedCardDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load tokenisedCard on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.tokenisedCard).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
