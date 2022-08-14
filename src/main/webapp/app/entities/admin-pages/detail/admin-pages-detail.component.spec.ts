import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { AdminPagesDetailComponent } from './admin-pages-detail.component';

describe('AdminPages Management Detail Component', () => {
  let comp: AdminPagesDetailComponent;
  let fixture: ComponentFixture<AdminPagesDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [AdminPagesDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ adminPages: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(AdminPagesDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(AdminPagesDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load adminPages on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.adminPages).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
