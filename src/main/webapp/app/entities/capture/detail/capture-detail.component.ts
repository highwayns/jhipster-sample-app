import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ICapture } from '../capture.model';

@Component({
  selector: 'jhi-capture-detail',
  templateUrl: './capture-detail.component.html',
})
export class CaptureDetailComponent implements OnInit {
  capture: ICapture | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ capture }) => {
      this.capture = capture;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
