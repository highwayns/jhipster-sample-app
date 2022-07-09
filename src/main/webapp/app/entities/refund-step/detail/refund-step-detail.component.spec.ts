import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { RefundStepDetailComponent } from './refund-step-detail.component';

describe('RefundStep Management Detail Component', () => {
  let comp: RefundStepDetailComponent;
  let fixture: ComponentFixture<RefundStepDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [RefundStepDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ refundStep: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(RefundStepDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(RefundStepDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load refundStep on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.refundStep).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
