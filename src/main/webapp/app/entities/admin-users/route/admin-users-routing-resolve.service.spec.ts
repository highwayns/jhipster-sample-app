import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, ActivatedRoute, Router, convertToParamMap } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { IAdminUsers, AdminUsers } from '../admin-users.model';
import { AdminUsersService } from '../service/admin-users.service';

import { AdminUsersRoutingResolveService } from './admin-users-routing-resolve.service';

describe('AdminUsers routing resolve service', () => {
  let mockRouter: Router;
  let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
  let routingResolveService: AdminUsersRoutingResolveService;
  let service: AdminUsersService;
  let resultAdminUsers: IAdminUsers | undefined;

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
    routingResolveService = TestBed.inject(AdminUsersRoutingResolveService);
    service = TestBed.inject(AdminUsersService);
    resultAdminUsers = undefined;
  });

  describe('resolve', () => {
    it('should return IAdminUsers returned by find', () => {
      // GIVEN
      service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultAdminUsers = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultAdminUsers).toEqual({ id: 123 });
    });

    it('should return new IAdminUsers if id is not provided', () => {
      // GIVEN
      service.find = jest.fn();
      mockActivatedRouteSnapshot.params = {};

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultAdminUsers = result;
      });

      // THEN
      expect(service.find).not.toBeCalled();
      expect(resultAdminUsers).toEqual(new AdminUsers());
    });

    it('should route to 404 page if data not found in server', () => {
      // GIVEN
      jest.spyOn(service, 'find').mockReturnValue(of(new HttpResponse({ body: null as unknown as AdminUsers })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultAdminUsers = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultAdminUsers).toEqual(undefined);
      expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
    });
  });
});
