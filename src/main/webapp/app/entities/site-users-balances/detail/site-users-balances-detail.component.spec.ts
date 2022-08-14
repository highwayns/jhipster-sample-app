import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { SiteUsersBalancesDetailComponent } from './site-users-balances-detail.component';

describe('SiteUsersBalances Management Detail Component', () => {
  let comp: SiteUsersBalancesDetailComponent;
  let fixture: ComponentFixture<SiteUsersBalancesDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [SiteUsersBalancesDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ siteUsersBalances: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(SiteUsersBalancesDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(SiteUsersBalancesDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load siteUsersBalances on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.siteUsersBalances).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
