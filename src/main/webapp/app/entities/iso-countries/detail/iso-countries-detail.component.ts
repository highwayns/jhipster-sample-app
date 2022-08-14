import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IIsoCountries } from '../iso-countries.model';

@Component({
  selector: 'jhi-iso-countries-detail',
  templateUrl: './iso-countries-detail.component.html',
})
export class IsoCountriesDetailComponent implements OnInit {
  isoCountries: IIsoCountries | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ isoCountries }) => {
      this.isoCountries = isoCountries;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
