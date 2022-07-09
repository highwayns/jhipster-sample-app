import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { CardTokenDataDetailComponent } from './card-token-data-detail.component';

describe('CardTokenData Management Detail Component', () => {
  let comp: CardTokenDataDetailComponent;
  let fixture: ComponentFixture<CardTokenDataDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [CardTokenDataDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ cardTokenData: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(CardTokenDataDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(CardTokenDataDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load cardTokenData on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.cardTokenData).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
