import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { IpAccessLogDetailComponent } from './ip-access-log-detail.component';

describe('IpAccessLog Management Detail Component', () => {
  let comp: IpAccessLogDetailComponent;
  let fixture: ComponentFixture<IpAccessLogDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [IpAccessLogDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ ipAccessLog: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(IpAccessLogDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(IpAccessLogDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load ipAccessLog on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.ipAccessLog).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
