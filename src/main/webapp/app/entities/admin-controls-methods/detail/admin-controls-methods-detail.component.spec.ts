import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { AdminControlsMethodsDetailComponent } from './admin-controls-methods-detail.component';

describe('AdminControlsMethods Management Detail Component', () => {
  let comp: AdminControlsMethodsDetailComponent;
  let fixture: ComponentFixture<AdminControlsMethodsDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [AdminControlsMethodsDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ adminControlsMethods: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(AdminControlsMethodsDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(AdminControlsMethodsDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load adminControlsMethods on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.adminControlsMethods).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
