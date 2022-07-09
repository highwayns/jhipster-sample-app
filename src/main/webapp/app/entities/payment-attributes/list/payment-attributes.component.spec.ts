import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { of } from 'rxjs';

import { PaymentAttributesService } from '../service/payment-attributes.service';

import { PaymentAttributesComponent } from './payment-attributes.component';

describe('PaymentAttributes Management Component', () => {
  let comp: PaymentAttributesComponent;
  let fixture: ComponentFixture<PaymentAttributesComponent>;
  let service: PaymentAttributesService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      declarations: [PaymentAttributesComponent],
    })
      .overrideTemplate(PaymentAttributesComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(PaymentAttributesComponent);
    comp = fixture.componentInstance;
    service = TestBed.inject(PaymentAttributesService);

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
    expect(comp.paymentAttributes?.[0]).toEqual(expect.objectContaining({ id: 123 }));
  });
});
