import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { SiteUsersCatchDetailComponent } from './site-users-catch-detail.component';

describe('SiteUsersCatch Management Detail Component', () => {
  let comp: SiteUsersCatchDetailComponent;
  let fixture: ComponentFixture<SiteUsersCatchDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [SiteUsersCatchDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ siteUsersCatch: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(SiteUsersCatchDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(SiteUsersCatchDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load siteUsersCatch on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.siteUsersCatch).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
