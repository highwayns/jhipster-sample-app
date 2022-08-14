import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, ActivatedRoute, Router, convertToParamMap } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { IAdminControlsMethods, AdminControlsMethods } from '../admin-controls-methods.model';
import { AdminControlsMethodsService } from '../service/admin-controls-methods.service';

import { AdminControlsMethodsRoutingResolveService } from './admin-controls-methods-routing-resolve.service';

describe('AdminControlsMethods routing resolve service', () => {
  let mockRouter: Router;
  let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
  let routingResolveService: AdminControlsMethodsRoutingResolveService;
  let service: AdminControlsMethodsService;
  let resultAdminControlsMethods: IAdminControlsMethods | undefined;

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
    routingResolveService = TestBed.inject(AdminControlsMethodsRoutingResolveService);
    service = TestBed.inject(AdminControlsMethodsService);
    resultAdminControlsMethods = undefined;
  });

  describe('resolve', () => {
    it('should return IAdminControlsMethods returned by find', () => {
      // GIVEN
      service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultAdminControlsMethods = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultAdminControlsMethods).toEqual({ id: 123 });
    });

    it('should return new IAdminControlsMethods if id is not provided', () => {
      // GIVEN
      service.find = jest.fn();
      mockActivatedRouteSnapshot.params = {};

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultAdminControlsMethods = result;
      });

      // THEN
      expect(service.find).not.toBeCalled();
      expect(resultAdminControlsMethods).toEqual(new AdminControlsMethods());
    });

    it('should route to 404 page if data not found in server', () => {
      // GIVEN
      jest.spyOn(service, 'find').mockReturnValue(of(new HttpResponse({ body: null as unknown as AdminControlsMethods })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultAdminControlsMethods = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultAdminControlsMethods).toEqual(undefined);
      expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
    });
  });
});
