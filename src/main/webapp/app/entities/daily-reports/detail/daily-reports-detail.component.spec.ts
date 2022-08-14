import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { DailyReportsDetailComponent } from './daily-reports-detail.component';

describe('DailyReports Management Detail Component', () => {
  let comp: DailyReportsDetailComponent;
  let fixture: ComponentFixture<DailyReportsDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [DailyReportsDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ dailyReports: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(DailyReportsDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(DailyReportsDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load dailyReports on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.dailyReports).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
