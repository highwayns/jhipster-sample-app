import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IIssuer } from '../issuer.model';

@Component({
  selector: 'jhi-issuer-detail',
  templateUrl: './issuer-detail.component.html',
})
export class IssuerDetailComponent implements OnInit {
  issuer: IIssuer | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ issuer }) => {
      this.issuer = issuer;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
