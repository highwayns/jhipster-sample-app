import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { PaymentJobDetailComponent } from './payment-job-detail.component';

describe('PaymentJob Management Detail Component', () => {
  let comp: PaymentJobDetailComponent;
  let fixture: ComponentFixture<PaymentJobDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [PaymentJobDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ paymentJob: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(PaymentJobDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(PaymentJobDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load paymentJob on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.paymentJob).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
