import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { FeeScheduleDetailComponent } from './fee-schedule-detail.component';

describe('FeeSchedule Management Detail Component', () => {
  let comp: FeeScheduleDetailComponent;
  let fixture: ComponentFixture<FeeScheduleDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [FeeScheduleDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ feeSchedule: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(FeeScheduleDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(FeeScheduleDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load feeSchedule on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.feeSchedule).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
