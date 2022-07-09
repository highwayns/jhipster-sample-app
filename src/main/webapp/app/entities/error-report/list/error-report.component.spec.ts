import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { of } from 'rxjs';

import { ErrorReportService } from '../service/error-report.service';

import { ErrorReportComponent } from './error-report.component';

describe('ErrorReport Management Component', () => {
  let comp: ErrorReportComponent;
  let fixture: ComponentFixture<ErrorReportComponent>;
  let service: ErrorReportService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      declarations: [ErrorReportComponent],
    })
      .overrideTemplate(ErrorReportComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(ErrorReportComponent);
    comp = fixture.componentInstance;
    service = TestBed.inject(ErrorReportService);

    const headers = new HttpHeaders();
    jest.spyOn(service, 'query').mockReturnValue(
      of(
        new HttpResponse({
          body: [{ id: 123 }],
          headers,
        })
      )
    );
  });

  it('Should call load all on init', () => {
    // WHEN
    comp.ngOnInit();

    // THEN
    expect(service.query).toHaveBeenCalled();
    expect(comp.errorReports?.[0]).toEqual(expect.objectContaining({ id: 123 }));
  });
});
