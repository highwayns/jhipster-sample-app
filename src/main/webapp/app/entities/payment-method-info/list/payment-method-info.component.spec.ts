import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { of } from 'rxjs';

import { PaymentMethodInfoService } from '../service/payment-method-info.service';

import { PaymentMethodInfoComponent } from './payment-method-info.component';

describe('PaymentMethodInfo Management Component', () => {
  let comp: PaymentMethodInfoComponent;
  let fixture: ComponentFixture<PaymentMethodInfoComponent>;
  let service: PaymentMethodInfoService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      declarations: [PaymentMethodInfoComponent],
    })
      .overrideTemplate(PaymentMethodInfoComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(PaymentMethodInfoComponent);
    comp = fixture.componentInstance;
    service = TestBed.inject(PaymentMethodInfoService);

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
    expect(comp.paymentMethodInfos?.[0]).toEqual(expect.objectContaining({ id: 123 }));
  });
});
