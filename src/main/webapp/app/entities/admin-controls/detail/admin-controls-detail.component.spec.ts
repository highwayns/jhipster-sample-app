import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { AdminControlsDetailComponent } from './admin-controls-detail.component';

describe('AdminControls Management Detail Component', () => {
  let comp: AdminControlsDetailComponent;
  let fixture: ComponentFixture<AdminControlsDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [AdminControlsDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ adminControls: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(AdminControlsDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(AdminControlsDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load adminControls on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.adminControls).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
