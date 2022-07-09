import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { PaymentJobAttributesDetailComponent } from './payment-job-attributes-detail.component';

describe('PaymentJobAttributes Management Detail Component', () => {
  let comp: PaymentJobAttributesDetailComponent;
  let fixture: ComponentFixture<PaymentJobAttributesDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [PaymentJobAttributesDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ paymentJobAttributes: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(PaymentJobAttributesDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(PaymentJobAttributesDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load paymentJobAttributes on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.paymentJobAttributes).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
