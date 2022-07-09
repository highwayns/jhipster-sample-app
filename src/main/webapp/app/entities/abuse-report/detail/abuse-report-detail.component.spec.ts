import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { AbuseReportDetailComponent } from './abuse-report-detail.component';

describe('AbuseReport Management Detail Component', () => {
  let comp: AbuseReportDetailComponent;
  let fixture: ComponentFixture<AbuseReportDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [AbuseReportDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ abuseReport: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(AbuseReportDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(AbuseReportDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load abuseReport on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.abuseReport).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
