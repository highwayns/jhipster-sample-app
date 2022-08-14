import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, ActivatedRoute, Router, convertToParamMap } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { ISiteUsers, SiteUsers } from '../site-users.model';
import { SiteUsersService } from '../service/site-users.service';

import { SiteUsersRoutingResolveService } from './site-users-routing-resolve.service';

describe('SiteUsers routing resolve service', () => {
  let mockRouter: Router;
  let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
  let routingResolveService: SiteUsersRoutingResolveService;
  let service: SiteUsersService;
  let resultSiteUsers: ISiteUsers | undefined;

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
    routingResolveService = TestBed.inject(SiteUsersRoutingResolveService);
    service = TestBed.inject(SiteUsersService);
    resultSiteUsers = undefined;
  });

  describe('resolve', () => {
    it('should return ISiteUsers returned by find', () => {
      // GIVEN
      service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultSiteUsers = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultSiteUsers).toEqual({ id: 123 });
    });

    it('should return new ISiteUsers if id is not provided', () => {
      // GIVEN
      service.find = jest.fn();
      mockActivatedRouteSnapshot.params = {};

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultSiteUsers = result;
      });

      // THEN
      expect(service.find).not.toBeCalled();
      expect(resultSiteUsers).toEqual(new SiteUsers());
    });

    it('should route to 404 page if data not found in server', () => {
      // GIVEN
      jest.spyOn(service, 'find').mockReturnValue(of(new HttpResponse({ body: null as unknown as SiteUsers })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultSiteUsers = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultSiteUsers).toEqual(undefined);
      expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
    });
  });
});
