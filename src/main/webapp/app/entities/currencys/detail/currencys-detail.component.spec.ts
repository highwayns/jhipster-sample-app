import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { CurrencysDetailComponent } from './currencys-detail.component';

describe('Currencys Management Detail Component', () => {
  let comp: CurrencysDetailComponent;
  let fixture: ComponentFixture<CurrencysDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [CurrencysDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ currencys: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(CurrencysDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(CurrencysDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load currencys on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.currencys).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
