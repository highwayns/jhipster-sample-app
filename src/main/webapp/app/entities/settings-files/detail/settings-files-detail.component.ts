import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ISettingsFiles } from '../settings-files.model';

@Component({
  selector: 'jhi-settings-files-detail',
  templateUrl: './settings-files-detail.component.html',
})
export class SettingsFilesDetailComponent implements OnInit {
  settingsFiles: ISettingsFiles | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ settingsFiles }) => {
      this.settingsFiles = settingsFiles;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
