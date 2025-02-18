import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IChangeSettings } from '../change-settings.model';
import { DataUtils } from 'app/core/util/data-util.service';

@Component({
  selector: 'jhi-change-settings-detail',
  templateUrl: './change-settings-detail.component.html',
})
export class ChangeSettingsDetailComponent implements OnInit {
  changeSettings: IChangeSettings | null = null;

  constructor(protected dataUtils: DataUtils, protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ changeSettings }) => {
      this.changeSettings = changeSettings;
    });
  }

  byteSize(base64String: string): string {
    return this.dataUtils.byteSize(base64String);
  }

  openFile(base64String: string, contentType: string | null | undefined): void {
    this.dataUtils.openFile(base64String, contentType);
  }

  previousState(): void {
    window.history.back();
  }
}
