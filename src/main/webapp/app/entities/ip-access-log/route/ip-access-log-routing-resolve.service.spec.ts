import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, ActivatedRoute, Router, convertToParamMap } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { IIpAccessLog, IpAccessLog } from '../ip-access-log.model';
import { IpAccessLogService } from '../service/ip-access-log.service';

import { IpAccessLogRoutingResolveService } from './ip-access-log-routing-resolve.service';

describe('IpAccessLog routing resolve service', () => {
  let mockRouter: Router;
  let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
  let routingResolveService: IpAccessLogRoutingResolveService;
  let service: IpAccessLogService;
  let resultIpAccessLog: IIpAccessLog | undefined;

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
    routingResolveService = TestBed.inject(IpAccessLogRoutingResolveService);
    service = TestBed.inject(IpAccessLogService);
    resultIpAccessLog = undefined;
  });

  describe('resolve', () => {
    it('should return IIpAccessLog returned by find', () => {
      // GIVEN
      service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultIpAccessLog = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultIpAccessLog).toEqual({ id: 123 });
    });

    it('should return new IIpAccessLog if id is not provided', () => {
      // GIVEN
      service.find = jest.fn();
      mockActivatedRouteSnapshot.params = {};

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultIpAccessLog = result;
      });

      // THEN
      expect(service.find).not.toBeCalled();
      expect(resultIpAccessLog).toEqual(new IpAccessLog());
    });

    it('should route to 404 page if data not found in server', () => {
      // GIVEN
      jest.spyOn(service, 'find').mockReturnValue(of(new HttpResponse({ body: null as unknown as IpAccessLog })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultIpAccessLog = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultIpAccessLog).toEqual(undefined);
      expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
    });
  });
});
