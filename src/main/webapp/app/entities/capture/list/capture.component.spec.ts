import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { of } from 'rxjs';

import { CaptureService } from '../service/capture.service';

import { CaptureComponent } from './capture.component';

describe('Capture Management Component', () => {
  let comp: CaptureComponent;
  let fixture: ComponentFixture<CaptureComponent>;
  let service: CaptureService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      declarations: [CaptureComponent],
    })
      .overrideTemplate(CaptureComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(CaptureComponent);
    comp = fixture.componentInstance;
    service = TestBed.inject(CaptureService);

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
    expect(comp.captures?.[0]).toEqual(expect.objectContaining({ id: 123 }));
  });
});
