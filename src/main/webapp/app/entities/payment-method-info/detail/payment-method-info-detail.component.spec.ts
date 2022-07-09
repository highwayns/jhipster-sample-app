import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { PaymentMethodInfoDetailComponent } from './payment-method-info-detail.component';

describe('PaymentMethodInfo Management Detail Component', () => {
  let comp: PaymentMethodInfoDetailComponent;
  let fixture: ComponentFixture<PaymentMethodInfoDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [PaymentMethodInfoDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ paymentMethodInfo: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(PaymentMethodInfoDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(PaymentMethodInfoDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load paymentMethodInfo on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.paymentMethodInfo).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
