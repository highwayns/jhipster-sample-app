import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IBitcoinAddresses } from '../bitcoin-addresses.model';

@Component({
  selector: 'jhi-bitcoin-addresses-detail',
  templateUrl: './bitcoin-addresses-detail.component.html',
})
export class BitcoinAddressesDetailComponent implements OnInit {
  bitcoinAddresses: IBitcoinAddresses | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ bitcoinAddresses }) => {
      this.bitcoinAddresses = bitcoinAddresses;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
