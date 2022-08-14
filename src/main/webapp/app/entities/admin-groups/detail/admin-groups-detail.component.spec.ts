import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { AdminGroupsDetailComponent } from './admin-groups-detail.component';

describe('AdminGroups Management Detail Component', () => {
  let comp: AdminGroupsDetailComponent;
  let fixture: ComponentFixture<AdminGroupsDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [AdminGroupsDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ adminGroups: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(AdminGroupsDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(AdminGroupsDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load adminGroups on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.adminGroups).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
