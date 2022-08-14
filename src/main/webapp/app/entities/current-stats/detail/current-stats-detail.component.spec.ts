import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { CurrentStatsDetailComponent } from './current-stats-detail.component';

describe('CurrentStats Management Detail Component', () => {
  let comp: CurrentStatsDetailComponent;
  let fixture: ComponentFixture<CurrentStatsDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [CurrentStatsDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ currentStats: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(CurrentStatsDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(CurrentStatsDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load currentStats on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.currentStats).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
