import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { AdminCronDetailComponent } from './admin-cron-detail.component';

describe('AdminCron Management Detail Component', () => {
  let comp: AdminCronDetailComponent;
  let fixture: ComponentFixture<AdminCronDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [AdminCronDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ adminCron: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(AdminCronDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(AdminCronDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load adminCron on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.adminCron).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
