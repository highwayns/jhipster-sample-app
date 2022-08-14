import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { MonthlyReportsDetailComponent } from './monthly-reports-detail.component';

describe('MonthlyReports Management Detail Component', () => {
  let comp: MonthlyReportsDetailComponent;
  let fixture: ComponentFixture<MonthlyReportsDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [MonthlyReportsDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ monthlyReports: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(MonthlyReportsDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(MonthlyReportsDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load monthlyReports on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.monthlyReports).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
