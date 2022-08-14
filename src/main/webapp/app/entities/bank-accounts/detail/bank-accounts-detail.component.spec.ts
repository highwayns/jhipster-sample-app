import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { BankAccountsDetailComponent } from './bank-accounts-detail.component';

describe('BankAccounts Management Detail Component', () => {
  let comp: BankAccountsDetailComponent;
  let fixture: ComponentFixture<BankAccountsDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [BankAccountsDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ bankAccounts: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(BankAccountsDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(BankAccountsDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load bankAccounts on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.bankAccounts).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
