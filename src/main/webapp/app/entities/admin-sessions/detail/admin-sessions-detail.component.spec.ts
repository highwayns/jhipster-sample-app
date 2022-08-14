import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { AdminSessionsDetailComponent } from './admin-sessions-detail.component';

describe('AdminSessions Management Detail Component', () => {
  let comp: AdminSessionsDetailComponent;
  let fixture: ComponentFixture<AdminSessionsDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [AdminSessionsDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ adminSessions: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(AdminSessionsDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(AdminSessionsDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load adminSessions on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.adminSessions).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
