import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, ActivatedRoute, Router, convertToParamMap } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { IAdminGroupsPages, AdminGroupsPages } from '../admin-groups-pages.model';
import { AdminGroupsPagesService } from '../service/admin-groups-pages.service';

import { AdminGroupsPagesRoutingResolveService } from './admin-groups-pages-routing-resolve.service';

describe('AdminGroupsPages routing resolve service', () => {
  let mockRouter: Router;
  let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
  let routingResolveService: AdminGroupsPagesRoutingResolveService;
  let service: AdminGroupsPagesService;
  let resultAdminGroupsPages: IAdminGroupsPages | undefined;

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
    routingResolveService = TestBed.inject(AdminGroupsPagesRoutingResolveService);
    service = TestBed.inject(AdminGroupsPagesService);
    resultAdminGroupsPages = undefined;
  });

  describe('resolve', () => {
    it('should return IAdminGroupsPages returned by find', () => {
      // GIVEN
      service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultAdminGroupsPages = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultAdminGroupsPages).toEqual({ id: 123 });
    });

    it('should return new IAdminGroupsPages if id is not provided', () => {
      // GIVEN
      service.find = jest.fn();
      mockActivatedRouteSnapshot.params = {};

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultAdminGroupsPages = result;
      });

      // THEN
      expect(service.find).not.toBeCalled();
      expect(resultAdminGroupsPages).toEqual(new AdminGroupsPages());
    });

    it('should route to 404 page if data not found in server', () => {
      // GIVEN
      jest.spyOn(service, 'find').mockReturnValue(of(new HttpResponse({ body: null as unknown as AdminGroupsPages })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultAdminGroupsPages = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultAdminGroupsPages).toEqual(undefined);
      expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
    });
  });
});
