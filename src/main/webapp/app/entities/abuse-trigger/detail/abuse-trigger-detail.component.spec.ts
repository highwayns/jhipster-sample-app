import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { AbuseTriggerDetailComponent } from './abuse-trigger-detail.component';

describe('AbuseTrigger Management Detail Component', () => {
  let comp: AbuseTriggerDetailComponent;
  let fixture: ComponentFixture<AbuseTriggerDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [AbuseTriggerDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ abuseTrigger: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(AbuseTriggerDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(AbuseTriggerDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load abuseTrigger on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.abuseTrigger).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
