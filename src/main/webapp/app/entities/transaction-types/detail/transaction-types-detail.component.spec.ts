import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { TransactionTypesDetailComponent } from './transaction-types-detail.component';

describe('TransactionTypes Management Detail Component', () => {
  let comp: TransactionTypesDetailComponent;
  let fixture: ComponentFixture<TransactionTypesDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [TransactionTypesDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ transactionTypes: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(TransactionTypesDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(TransactionTypesDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load transactionTypes on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.transactionTypes).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
