import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { OrderLogDetailComponent } from './order-log-detail.component';

describe('OrderLog Management Detail Component', () => {
  let comp: OrderLogDetailComponent;
  let fixture: ComponentFixture<OrderLogDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [OrderLogDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ orderLog: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(OrderLogDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(OrderLogDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load orderLog on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.orderLog).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
