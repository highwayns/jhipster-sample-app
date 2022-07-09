import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { PaymentStepDetailComponent } from './payment-step-detail.component';

describe('PaymentStep Management Detail Component', () => {
  let comp: PaymentStepDetailComponent;
  let fixture: ComponentFixture<PaymentStepDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [PaymentStepDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ paymentStep: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(PaymentStepDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(PaymentStepDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load paymentStep on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.paymentStep).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
