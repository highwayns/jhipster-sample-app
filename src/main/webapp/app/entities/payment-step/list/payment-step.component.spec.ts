import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { of } from 'rxjs';

import { PaymentStepService } from '../service/payment-step.service';

import { PaymentStepComponent } from './payment-step.component';

describe('PaymentStep Management Component', () => {
  let comp: PaymentStepComponent;
  let fixture: ComponentFixture<PaymentStepComponent>;
  let service: PaymentStepService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      declarations: [PaymentStepComponent],
    })
      .overrideTemplate(PaymentStepComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(PaymentStepComponent);
    comp = fixture.componentInstance;
    service = TestBed.inject(PaymentStepService);

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
    expect(comp.paymentSteps?.[0]).toEqual(expect.objectContaining({ id: 123 }));
  });
});
