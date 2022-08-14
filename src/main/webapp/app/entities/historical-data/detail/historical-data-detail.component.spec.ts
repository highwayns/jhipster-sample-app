import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { HistoricalDataDetailComponent } from './historical-data-detail.component';

describe('HistoricalData Management Detail Component', () => {
  let comp: HistoricalDataDetailComponent;
  let fixture: ComponentFixture<HistoricalDataDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [HistoricalDataDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ historicalData: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(HistoricalDataDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(HistoricalDataDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load historicalData on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.historicalData).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
