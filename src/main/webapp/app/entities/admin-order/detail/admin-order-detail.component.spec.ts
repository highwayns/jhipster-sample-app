import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { AdminOrderDetailComponent } from './admin-order-detail.component';

describe('AdminOrder Management Detail Component', () => {
  let comp: AdminOrderDetailComponent;
  let fixture: ComponentFixture<AdminOrderDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [AdminOrderDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ adminOrder: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(AdminOrderDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(AdminOrderDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load adminOrder on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.adminOrder).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
