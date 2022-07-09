import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { of } from 'rxjs';

import { RefundStepService } from '../service/refund-step.service';

import { RefundStepComponent } from './refund-step.component';

describe('RefundStep Management Component', () => {
  let comp: RefundStepComponent;
  let fixture: ComponentFixture<RefundStepComponent>;
  let service: RefundStepService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      declarations: [RefundStepComponent],
    })
      .overrideTemplate(RefundStepComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(RefundStepComponent);
    comp = fixture.componentInstance;
    service = TestBed.inject(RefundStepService);

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
    expect(comp.refundSteps?.[0]).toEqual(expect.objectContaining({ id: 123 }));
  });
});
