import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { RecurrenceCriteriaDetailComponent } from './recurrence-criteria-detail.component';

describe('RecurrenceCriteria Management Detail Component', () => {
  let comp: RecurrenceCriteriaDetailComponent;
  let fixture: ComponentFixture<RecurrenceCriteriaDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [RecurrenceCriteriaDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ recurrenceCriteria: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(RecurrenceCriteriaDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(RecurrenceCriteriaDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load recurrenceCriteria on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.recurrenceCriteria).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
