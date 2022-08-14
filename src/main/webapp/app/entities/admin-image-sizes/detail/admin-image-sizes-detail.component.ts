import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IAdminImageSizes } from '../admin-image-sizes.model';

@Component({
  selector: 'jhi-admin-image-sizes-detail',
  templateUrl: './admin-image-sizes-detail.component.html',
})
export class AdminImageSizesDetailComponent implements OnInit {
  adminImageSizes: IAdminImageSizes | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ adminImageSizes }) => {
      this.adminImageSizes = adminImageSizes;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
