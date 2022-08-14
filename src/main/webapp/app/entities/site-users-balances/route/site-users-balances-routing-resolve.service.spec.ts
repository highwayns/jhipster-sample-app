import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, ActivatedRoute, Router, convertToParamMap } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { ISiteUsersBalances, SiteUsersBalances } from '../site-users-balances.model';
import { SiteUsersBalancesService } from '../service/site-users-balances.service';

import { SiteUsersBalancesRoutingResolveService } from './site-users-balances-routing-resolve.service';

describe('SiteUsersBalances routing resolve service', () => {
  let mockRouter: Router;
  let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
  let routingResolveService: SiteUsersBalancesRoutingResolveService;
  let service: SiteUsersBalancesService;
  let resultSiteUsersBalances: ISiteUsersBalances | undefined;

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
    routingResolveService = TestBed.inject(SiteUsersBalancesRoutingResolveService);
    service = TestBed.inject(SiteUsersBalancesService);
    resultSiteUsersBalances = undefined;
  });

  describe('resolve', () => {
    it('should return ISiteUsersBalances returned by find', () => {
      // GIVEN
      service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultSiteUsersBalances = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultSiteUsersBalances).toEqual({ id: 123 });
    });

    it('should return new ISiteUsersBalances if id is not provided', () => {
      // GIVEN
      service.find = jest.fn();
      mockActivatedRouteSnapshot.params = {};

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultSiteUsersBalances = result;
      });

      // THEN
      expect(service.find).not.toBeCalled();
      expect(resultSiteUsersBalances).toEqual(new SiteUsersBalances());
    });

    it('should route to 404 page if data not found in server', () => {
      // GIVEN
      jest.spyOn(service, 'find').mockReturnValue(of(new HttpResponse({ body: null as unknown as SiteUsersBalances })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultSiteUsersBalances = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultSiteUsersBalances).toEqual(undefined);
      expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
    });
  });
});
