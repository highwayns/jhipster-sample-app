import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { CaptureDetailComponent } from './capture-detail.component';

describe('Capture Management Detail Component', () => {
  let comp: CaptureDetailComponent;
  let fixture: ComponentFixture<CaptureDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [CaptureDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ capture: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(CaptureDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(CaptureDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load capture on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.capture).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
