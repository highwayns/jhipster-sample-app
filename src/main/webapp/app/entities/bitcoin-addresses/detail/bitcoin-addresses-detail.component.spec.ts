import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { BitcoinAddressesDetailComponent } from './bitcoin-addresses-detail.component';

describe('BitcoinAddresses Management Detail Component', () => {
  let comp: BitcoinAddressesDetailComponent;
  let fixture: ComponentFixture<BitcoinAddressesDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [BitcoinAddressesDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ bitcoinAddresses: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(BitcoinAddressesDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(BitcoinAddressesDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load bitcoinAddresses on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.bitcoinAddresses).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
