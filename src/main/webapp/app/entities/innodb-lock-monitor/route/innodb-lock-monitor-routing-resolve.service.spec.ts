import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, ActivatedRoute, Router, convertToParamMap } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { IInnodbLockMonitor, InnodbLockMonitor } from '../innodb-lock-monitor.model';
import { InnodbLockMonitorService } from '../service/innodb-lock-monitor.service';

import { InnodbLockMonitorRoutingResolveService } from './innodb-lock-monitor-routing-resolve.service';

describe('InnodbLockMonitor routing resolve service', () => {
  let mockRouter: Router;
  let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
  let routingResolveService: InnodbLockMonitorRoutingResolveService;
  let service: InnodbLockMonitorService;
  let resultInnodbLockMonitor: IInnodbLockMonitor | undefined;

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
    routingResolveService = TestBed.inject(InnodbLockMonitorRoutingResolveService);
    service = TestBed.inject(InnodbLockMonitorService);
    resultInnodbLockMonitor = undefined;
  });

  describe('resolve', () => {
    it('should return IInnodbLockMonitor returned by find', () => {
      // GIVEN
      service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultInnodbLockMonitor = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultInnodbLockMonitor).toEqual({ id: 123 });
    });

    it('should return new IInnodbLockMonitor if id is not provided', () => {
      // GIVEN
      service.find = jest.fn();
      mockActivatedRouteSnapshot.params = {};

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultInnodbLockMonitor = result;
      });

      // THEN
      expect(service.find).not.toBeCalled();
      expect(resultInnodbLockMonitor).toEqual(new InnodbLockMonitor());
    });

    it('should route to 404 page if data not found in server', () => {
      // GIVEN
      jest.spyOn(service, 'find').mockReturnValue(of(new HttpResponse({ body: null as unknown as InnodbLockMonitor })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultInnodbLockMonitor = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultInnodbLockMonitor).toEqual(undefined);
      expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
    });
  });
});
