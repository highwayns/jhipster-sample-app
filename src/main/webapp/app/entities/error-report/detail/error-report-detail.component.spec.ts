import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { ErrorReportDetailComponent } from './error-report-detail.component';

describe('ErrorReport Management Detail Component', () => {
  let comp: ErrorReportDetailComponent;
  let fixture: ComponentFixture<ErrorReportDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [ErrorReportDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ errorReport: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(ErrorReportDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(ErrorReportDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load errorReport on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.errorReport).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
