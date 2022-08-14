import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { SiteUsersDetailComponent } from './site-users-detail.component';

describe('SiteUsers Management Detail Component', () => {
  let comp: SiteUsersDetailComponent;
  let fixture: ComponentFixture<SiteUsersDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [SiteUsersDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ siteUsers: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(SiteUsersDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(SiteUsersDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load siteUsers on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.siteUsers).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
