import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IEmails } from '../emails.model';
import { DataUtils } from 'app/core/util/data-util.service';

@Component({
  selector: 'jhi-emails-detail',
  templateUrl: './emails-detail.component.html',
})
export class EmailsDetailComponent implements OnInit {
  emails: IEmails | null = null;

  constructor(protected dataUtils: DataUtils, protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ emails }) => {
      this.emails = emails;
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
