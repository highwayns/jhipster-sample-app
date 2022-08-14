import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { AdminGroupsTabsDetailComponent } from './admin-groups-tabs-detail.component';

describe('AdminGroupsTabs Management Detail Component', () => {
  let comp: AdminGroupsTabsDetailComponent;
  let fixture: ComponentFixture<AdminGroupsTabsDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [AdminGroupsTabsDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ adminGroupsTabs: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(AdminGroupsTabsDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(AdminGroupsTabsDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load adminGroupsTabs on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.adminGroupsTabs).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
