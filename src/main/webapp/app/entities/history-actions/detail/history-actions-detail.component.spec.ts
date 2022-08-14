import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { HistoryActionsDetailComponent } from './history-actions-detail.component';

describe('HistoryActions Management Detail Component', () => {
  let comp: HistoryActionsDetailComponent;
  let fixture: ComponentFixture<HistoryActionsDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [HistoryActionsDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ historyActions: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(HistoryActionsDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(HistoryActionsDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load historyActions on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.historyActions).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
