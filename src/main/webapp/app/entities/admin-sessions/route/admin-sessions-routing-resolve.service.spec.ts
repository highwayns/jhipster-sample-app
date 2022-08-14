import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, ActivatedRoute, Router, convertToParamMap } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { IAdminSessions, AdminSessions } from '../admin-sessions.model';
import { AdminSessionsService } from '../service/admin-sessions.service';

import { AdminSessionsRoutingResolveService } from './admin-sessions-routing-resolve.service';

describe('AdminSessions routing resolve service', () => {
  let mockRouter: Router;
  let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
  let routingResolveService: AdminSessionsRoutingResolveService;
  let service: AdminSessionsService;
  let resultAdminSessions: IAdminSessions | undefined;

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
    routingResolveService = TestBed.inject(AdminSessionsRoutingResolveService);
    service = TestBed.inject(AdminSessionsService);
    resultAdminSessions = undefined;
  });

  describe('resolve', () => {
    it('should return IAdminSessions returned by find', () => {
      // GIVEN
      service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultAdminSessions = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultAdminSessions).toEqual({ id: 123 });
    });

    it('should return new IAdminSessions if id is not provided', () => {
      // GIVEN
      service.find = jest.fn();
      mockActivatedRouteSnapshot.params = {};

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultAdminSessions = result;
      });

      // THEN
      expect(service.find).not.toBeCalled();
      expect(resultAdminSessions).toEqual(new AdminSessions());
    });

    it('should route to 404 page if data not found in server', () => {
      // GIVEN
      jest.spyOn(service, 'find').mockReturnValue(of(new HttpResponse({ body: null as unknown as AdminSessions })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultAdminSessions = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultAdminSessions).toEqual(undefined);
      expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
    });
  });
});
