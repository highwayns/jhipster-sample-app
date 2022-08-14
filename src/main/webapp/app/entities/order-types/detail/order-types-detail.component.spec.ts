import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { OrderTypesDetailComponent } from './order-types-detail.component';

describe('OrderTypes Management Detail Component', () => {
  let comp: OrderTypesDetailComponent;
  let fixture: ComponentFixture<OrderTypesDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [OrderTypesDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ orderTypes: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(OrderTypesDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(OrderTypesDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load orderTypes on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.orderTypes).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
