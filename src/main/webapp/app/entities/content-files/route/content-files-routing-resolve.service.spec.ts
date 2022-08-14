import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, ActivatedRoute, Router, convertToParamMap } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { IContentFiles, ContentFiles } from '../content-files.model';
import { ContentFilesService } from '../service/content-files.service';

import { ContentFilesRoutingResolveService } from './content-files-routing-resolve.service';

describe('ContentFiles routing resolve service', () => {
  let mockRouter: Router;
  let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
  let routingResolveService: ContentFilesRoutingResolveService;
  let service: ContentFilesService;
  let resultContentFiles: IContentFiles | undefined;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: {
            snapshot: {
              paramMap: convertToParamMap({}),
            },
          },
        },
      ],
    });
    mockRouter = TestBed.inject(Router);
    jest.spyOn(mockRouter, 'navigate').mockImplementation(() => Promise.resolve(true));
    mockActivatedRouteSnapshot = TestBed.inject(ActivatedRoute).snapshot;
    routingResolveService = TestBed.inject(ContentFilesRoutingResolveService);
    service = TestBed.inject(ContentFilesService);
    resultContentFiles = undefined;
  });

  describe('resolve', () => {
    it('should return IContentFiles returned by find', () => {
      // GIVEN
      service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultContentFiles = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultContentFiles).toEqual({ id: 123 });
    });

    it('should return new IContentFiles if id is not provided', () => {
      // GIVEN
      service.find = jest.fn();
      mockActivatedRouteSnapshot.params = {};

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultContentFiles = result;
      });

      // THEN
      expect(service.find).not.toBeCalled();
      expect(resultContentFiles).toEqual(new ContentFiles());
    });

    it('should route to 404 page if data not found in server', () => {
      // GIVEN
      jest.spyOn(service, 'find').mockReturnValue(of(new HttpResponse({ body: null as unknown as ContentFiles })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultContentFiles = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultContentFiles).toEqual(undefined);
      expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
    });
  });
});
