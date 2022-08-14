import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IContentFiles, ContentFiles } from '../content-files.model';
import { ContentFilesService } from '../service/content-files.service';

@Injectable({ providedIn: 'root' })
export class ContentFilesRoutingResolveService implements Resolve<IContentFiles> {
  constructor(protected service: ContentFilesService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IContentFiles> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((contentFiles: HttpResponse<ContentFiles>) => {
          if (contentFiles.body) {
            return of(contentFiles.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new ContentFiles());
  }
}
