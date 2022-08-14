import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { InnodbLockMonitorDetailComponent } from './innodb-lock-monitor-detail.component';

describe('InnodbLockMonitor Management Detail Component', () => {
  let comp: InnodbLockMonitorDetailComponent;
  let fixture: ComponentFixture<InnodbLockMonitorDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [InnodbLockMonitorDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ innodbLockMonitor: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(InnodbLockMonitorDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(InnodbLockMonitorDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load innodbLockMonitor on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.innodbLockMonitor).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
