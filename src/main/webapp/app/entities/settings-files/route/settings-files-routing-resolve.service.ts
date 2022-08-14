import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { ISettingsFiles, SettingsFiles } from '../settings-files.model';
import { SettingsFilesService } from '../service/settings-files.service';

@Injectable({ providedIn: 'root' })
export class SettingsFilesRoutingResolveService implements Resolve<ISettingsFiles> {
  constructor(protected service: SettingsFilesService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ISettingsFiles> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((settingsFiles: HttpResponse<SettingsFiles>) => {
          if (settingsFiles.body) {
            return of(settingsFiles.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new SettingsFiles());
  }
}
