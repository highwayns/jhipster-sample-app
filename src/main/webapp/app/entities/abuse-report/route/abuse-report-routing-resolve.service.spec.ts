import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, ActivatedRoute, Router, convertToParamMap } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { IAbuseReport, AbuseReport } from '../abuse-report.model';
import { AbuseReportService } from '../service/abuse-report.service';

import { AbuseReportRoutingResolveService } from './abuse-report-routing-resolve.service';

describe('AbuseReport routing resolve service', () => {
  let mockRouter: Router;
  let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
  let routingResolveService: AbuseReportRoutingResolveService;
  let service: AbuseReportService;
  let resultAbuseReport: IAbuseReport | undefined;

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
    routingResolveService = TestBed.inject(AbuseReportRoutingResolveService);
    service = TestBed.inject(AbuseReportService);
    resultAbuseReport = undefined;
  });

  describe('resolve', () => {
    it('should return IAbuseReport returned by find', () => {
      // GIVEN
      service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultAbuseReport = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultAbuseReport).toEqual({ id: 123 });
    });

    it('should return new IAbuseReport if id is not provided', () => {
      // GIVEN
      service.find = jest.fn();
      mockActivatedRouteSnapshot.params = {};

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultAbuseReport = result;
      });

      // THEN
      expect(service.find).not.toBeCalled();
      expect(resultAbuseReport).toEqual(new AbuseReport());
    });

    it('should route to 404 page if data not found in server', () => {
      // GIVEN
      jest.spyOn(service, 'find').mockReturnValue(of(new HttpResponse({ body: null as unknown as AbuseReport })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultAbuseReport = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultAbuseReport).toEqual(undefined);
      expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
    });
  });
});
