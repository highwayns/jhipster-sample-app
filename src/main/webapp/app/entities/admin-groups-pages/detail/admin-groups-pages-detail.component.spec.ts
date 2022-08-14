import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { AdminGroupsPagesDetailComponent } from './admin-groups-pages-detail.component';

describe('AdminGroupsPages Management Detail Component', () => {
  let comp: AdminGroupsPagesDetailComponent;
  let fixture: ComponentFixture<AdminGroupsPagesDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [AdminGroupsPagesDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ adminGroupsPages: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(AdminGroupsPagesDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(AdminGroupsPagesDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load adminGroupsPages on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.adminGroupsPages).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
