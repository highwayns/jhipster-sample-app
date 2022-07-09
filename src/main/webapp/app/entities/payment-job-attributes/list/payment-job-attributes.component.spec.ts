import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { of } from 'rxjs';

import { PaymentJobAttributesService } from '../service/payment-job-attributes.service';

import { PaymentJobAttributesComponent } from './payment-job-attributes.component';

describe('PaymentJobAttributes Management Component', () => {
  let comp: PaymentJobAttributesComponent;
  let fixture: ComponentFixture<PaymentJobAttributesComponent>;
  let service: PaymentJobAttributesService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      declarations: [PaymentJobAttributesComponent],
    })
      .overrideTemplate(PaymentJobAttributesComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(PaymentJobAttributesComponent);
    comp = fixture.componentInstance;
    service = TestBed.inject(PaymentJobAttributesService);

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
    expect(comp.paymentJobAttributes?.[0]).toEqual(expect.objectContaining({ id: 123 }));
  });
});
