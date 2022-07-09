import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { PaymentAttributesDetailComponent } from './payment-attributes-detail.component';

describe('PaymentAttributes Management Detail Component', () => {
  let comp: PaymentAttributesDetailComponent;
  let fixture: ComponentFixture<PaymentAttributesDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [PaymentAttributesDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ paymentAttributes: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(PaymentAttributesDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(PaymentAttributesDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load paymentAttributes on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.paymentAttributes).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
