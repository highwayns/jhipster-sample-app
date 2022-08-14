import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, ActivatedRoute, Router, convertToParamMap } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { IAdminControls, AdminControls } from '../admin-controls.model';
import { AdminControlsService } from '../service/admin-controls.service';

import { AdminControlsRoutingResolveService } from './admin-controls-routing-resolve.service';

describe('AdminControls routing resolve service', () => {
  let mockRouter: Router;
  let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
  let routingResolveService: AdminControlsRoutingResolveService;
  let service: AdminControlsService;
  let resultAdminControls: IAdminControls | undefined;

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
    routingResolveService = TestBed.inject(AdminControlsRoutingResolveService);
    service = TestBed.inject(AdminControlsService);
    resultAdminControls = undefined;
  });

  describe('resolve', () => {
    it('should return IAdminControls returned by find', () => {
      // GIVEN
      service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultAdminControls = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultAdminControls).toEqual({ id: 123 });
    });

    it('should return new IAdminControls if id is not provided', () => {
      // GIVEN
      service.find = jest.fn();
      mockActivatedRouteSnapshot.params = {};

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultAdminControls = result;
      });

      // THEN
      expect(service.find).not.toBeCalled();
      expect(resultAdminControls).toEqual(new AdminControls());
    });

    it('should route to 404 page if data not found in server', () => {
      // GIVEN
      jest.spyOn(service, 'find').mockReturnValue(of(new HttpResponse({ body: null as unknown as AdminControls })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultAdminControls = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultAdminControls).toEqual(undefined);
      expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
    });
  });
});
