import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, ActivatedRoute, Router, convertToParamMap } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { IAdminTabs, AdminTabs } from '../admin-tabs.model';
import { AdminTabsService } from '../service/admin-tabs.service';

import { AdminTabsRoutingResolveService } from './admin-tabs-routing-resolve.service';

describe('AdminTabs routing resolve service', () => {
  let mockRouter: Router;
  let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
  let routingResolveService: AdminTabsRoutingResolveService;
  let service: AdminTabsService;
  let resultAdminTabs: IAdminTabs | undefined;

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
    routingResolveService = TestBed.inject(AdminTabsRoutingResolveService);
    service = TestBed.inject(AdminTabsService);
    resultAdminTabs = undefined;
  });

  describe('resolve', () => {
    it('should return IAdminTabs returned by find', () => {
      // GIVEN
      service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultAdminTabs = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultAdminTabs).toEqual({ id: 123 });
    });

    it('should return new IAdminTabs if id is not provided', () => {
      // GIVEN
      service.find = jest.fn();
      mockActivatedRouteSnapshot.params = {};

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultAdminTabs = result;
      });

      // THEN
      expect(service.find).not.toBeCalled();
      expect(resultAdminTabs).toEqual(new AdminTabs());
    });

    it('should route to 404 page if data not found in server', () => {
      // GIVEN
      jest.spyOn(service, 'find').mockReturnValue(of(new HttpResponse({ body: null as unknown as AdminTabs })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultAdminTabs = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultAdminTabs).toEqual(undefined);
      expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
    });
  });
});
