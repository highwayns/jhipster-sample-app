import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { AdminUsersDetailComponent } from './admin-users-detail.component';

describe('AdminUsers Management Detail Component', () => {
  let comp: AdminUsersDetailComponent;
  let fixture: ComponentFixture<AdminUsersDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [AdminUsersDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ adminUsers: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(AdminUsersDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(AdminUsersDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load adminUsers on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.adminUsers).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
