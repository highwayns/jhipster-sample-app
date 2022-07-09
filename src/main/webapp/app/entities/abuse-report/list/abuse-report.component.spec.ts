import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { of } from 'rxjs';

import { AbuseReportService } from '../service/abuse-report.service';

import { AbuseReportComponent } from './abuse-report.component';

describe('AbuseReport Management Component', () => {
  let comp: AbuseReportComponent;
  let fixture: ComponentFixture<AbuseReportComponent>;
  let service: AbuseReportService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      declarations: [AbuseReportComponent],
    })
      .overrideTemplate(AbuseReportComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(AbuseReportComponent);
    comp = fixture.componentInstance;
    service = TestBed.inject(AbuseReportService);

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
    expect(comp.abuseReports?.[0]).toEqual(expect.objectContaining({ id: 123 }));
  });
});
