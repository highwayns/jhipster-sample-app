import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, ActivatedRoute, Router, convertToParamMap } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { IApiKeys, ApiKeys } from '../api-keys.model';
import { ApiKeysService } from '../service/api-keys.service';

import { ApiKeysRoutingResolveService } from './api-keys-routing-resolve.service';

describe('ApiKeys routing resolve service', () => {
  let mockRouter: Router;
  let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
  let routingResolveService: ApiKeysRoutingResolveService;
  let service: ApiKeysService;
  let resultApiKeys: IApiKeys | undefined;

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
    routingResolveService = TestBed.inject(ApiKeysRoutingResolveService);
    service = TestBed.inject(ApiKeysService);
    resultApiKeys = undefined;
  });

  describe('resolve', () => {
    it('should return IApiKeys returned by find', () => {
      // GIVEN
      service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultApiKeys = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultApiKeys).toEqual({ id: 123 });
    });

    it('should return new IApiKeys if id is not provided', () => {
      // GIVEN
      service.find = jest.fn();
      mockActivatedRouteSnapshot.params = {};

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultApiKeys = result;
      });

      // THEN
      expect(service.find).not.toBeCalled();
      expect(resultApiKeys).toEqual(new ApiKeys());
    });

    it('should route to 404 page if data not found in server', () => {
      // GIVEN
      jest.spyOn(service, 'find').mockReturnValue(of(new HttpResponse({ body: null as unknown as ApiKeys })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultApiKeys = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultApiKeys).toEqual(undefined);
      expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
    });
  });
});
