import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, ActivatedRoute, Router, convertToParamMap } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { IAdminGroupsTabs, AdminGroupsTabs } from '../admin-groups-tabs.model';
import { AdminGroupsTabsService } from '../service/admin-groups-tabs.service';

import { AdminGroupsTabsRoutingResolveService } from './admin-groups-tabs-routing-resolve.service';

describe('AdminGroupsTabs routing resolve service', () => {
  let mockRouter: Router;
  let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
  let routingResolveService: AdminGroupsTabsRoutingResolveService;
  let service: AdminGroupsTabsService;
  let resultAdminGroupsTabs: IAdminGroupsTabs | undefined;

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
    routingResolveService = TestBed.inject(AdminGroupsTabsRoutingResolveService);
    service = TestBed.inject(AdminGroupsTabsService);
    resultAdminGroupsTabs = undefined;
  });

  describe('resolve', () => {
    it('should return IAdminGroupsTabs returned by find', () => {
      // GIVEN
      service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultAdminGroupsTabs = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultAdminGroupsTabs).toEqual({ id: 123 });
    });

    it('should return new IAdminGroupsTabs if id is not provided', () => {
      // GIVEN
      service.find = jest.fn();
      mockActivatedRouteSnapshot.params = {};

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultAdminGroupsTabs = result;
      });

      // THEN
      expect(service.find).not.toBeCalled();
      expect(resultAdminGroupsTabs).toEqual(new AdminGroupsTabs());
    });

    it('should route to 404 page if data not found in server', () => {
      // GIVEN
      jest.spyOn(service, 'find').mockReturnValue(of(new HttpResponse({ body: null as unknown as AdminGroupsTabs })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultAdminGroupsTabs = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultAdminGroupsTabs).toEqual(undefined);
      expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
    });
  });
});
