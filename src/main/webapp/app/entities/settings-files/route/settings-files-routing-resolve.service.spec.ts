import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, ActivatedRoute, Router, convertToParamMap } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { ISettingsFiles, SettingsFiles } from '../settings-files.model';
import { SettingsFilesService } from '../service/settings-files.service';

import { SettingsFilesRoutingResolveService } from './settings-files-routing-resolve.service';

describe('SettingsFiles routing resolve service', () => {
  let mockRouter: Router;
  let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
  let routingResolveService: SettingsFilesRoutingResolveService;
  let service: SettingsFilesService;
  let resultSettingsFiles: ISettingsFiles | undefined;

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
    routingResolveService = TestBed.inject(SettingsFilesRoutingResolveService);
    service = TestBed.inject(SettingsFilesService);
    resultSettingsFiles = undefined;
  });

  describe('resolve', () => {
    it('should return ISettingsFiles returned by find', () => {
      // GIVEN
      service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultSettingsFiles = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultSettingsFiles).toEqual({ id: 123 });
    });

    it('should return new ISettingsFiles if id is not provided', () => {
      // GIVEN
      service.find = jest.fn();
      mockActivatedRouteSnapshot.params = {};

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultSettingsFiles = result;
      });

      // THEN
      expect(service.find).not.toBeCalled();
      expect(resultSettingsFiles).toEqual(new SettingsFiles());
    });

    it('should route to 404 page if data not found in server', () => {
      // GIVEN
      jest.spyOn(service, 'find').mockReturnValue(of(new HttpResponse({ body: null as unknown as SettingsFiles })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultSettingsFiles = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultSettingsFiles).toEqual(undefined);
      expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
    });
  });
});
