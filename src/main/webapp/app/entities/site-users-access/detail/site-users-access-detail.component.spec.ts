import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { SiteUsersAccessDetailComponent } from './site-users-access-detail.component';

describe('SiteUsersAccess Management Detail Component', () => {
  let comp: SiteUsersAccessDetailComponent;
  let fixture: ComponentFixture<SiteUsersAccessDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [SiteUsersAccessDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ siteUsersAccess: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(SiteUsersAccessDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(SiteUsersAccessDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load siteUsersAccess on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.siteUsersAccess).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
