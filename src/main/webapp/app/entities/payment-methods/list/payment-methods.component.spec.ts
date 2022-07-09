import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { of } from 'rxjs';

import { PaymentMethodsService } from '../service/payment-methods.service';

import { PaymentMethodsComponent } from './payment-methods.component';

describe('PaymentMethods Management Component', () => {
  let comp: PaymentMethodsComponent;
  let fixture: ComponentFixture<PaymentMethodsComponent>;
  let service: PaymentMethodsService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      declarations: [PaymentMethodsComponent],
    })
      .overrideTemplate(PaymentMethodsComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(PaymentMethodsComponent);
    comp = fixture.componentInstance;
    service = TestBed.inject(PaymentMethodsService);

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
    expect(comp.paymentMethods?.[0]).toEqual(expect.objectContaining({ id: 123 }));
  });
});
