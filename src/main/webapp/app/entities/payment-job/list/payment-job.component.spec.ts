import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { of } from 'rxjs';

import { PaymentJobService } from '../service/payment-job.service';

import { PaymentJobComponent } from './payment-job.component';

describe('PaymentJob Management Component', () => {
  let comp: PaymentJobComponent;
  let fixture: ComponentFixture<PaymentJobComponent>;
  let service: PaymentJobService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      declarations: [PaymentJobComponent],
    })
      .overrideTemplate(PaymentJobComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(PaymentJobComponent);
    comp = fixture.componentInstance;
    service = TestBed.inject(PaymentJobService);

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
    expect(comp.paymentJobs?.[0]).toEqual(expect.objectContaining({ id: 123 }));
  });
});
