import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { AdminTabsDetailComponent } from './admin-tabs-detail.component';

describe('AdminTabs Management Detail Component', () => {
  let comp: AdminTabsDetailComponent;
  let fixture: ComponentFixture<AdminTabsDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [AdminTabsDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ adminTabs: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(AdminTabsDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(AdminTabsDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load adminTabs on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.adminTabs).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
