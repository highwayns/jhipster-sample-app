import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, ActivatedRoute, Router, convertToParamMap } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { IDailyReports, DailyReports } from '../daily-reports.model';
import { DailyReportsService } from '../service/daily-reports.service';

import { DailyReportsRoutingResolveService } from './daily-reports-routing-resolve.service';

describe('DailyReports routing resolve service', () => {
  let mockRouter: Router;
  let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
  let routingResolveService: DailyReportsRoutingResolveService;
  let service: DailyReportsService;
  let resultDailyReports: IDailyReports | undefined;

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
    routingResolveService = TestBed.inject(DailyReportsRoutingResolveService);
    service = TestBed.inject(DailyReportsService);
    resultDailyReports = undefined;
  });

  describe('resolve', () => {
    it('should return IDailyReports returned by find', () => {
      // GIVEN
      service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultDailyReports = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultDailyReports).toEqual({ id: 123 });
    });

    it('should return new IDailyReports if id is not provided', () => {
      // GIVEN
      service.find = jest.fn();
      mockActivatedRouteSnapshot.params = {};

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultDailyReports = result;
      });

      // THEN
      expect(service.find).not.toBeCalled();
      expect(resultDailyReports).toEqual(new DailyReports());
    });

    it('should route to 404 page if data not found in server', () => {
      // GIVEN
      jest.spyOn(service, 'find').mockReturnValue(of(new HttpResponse({ body: null as unknown as DailyReports })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultDailyReports = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultDailyReports).toEqual(undefined);
      expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
    });
  });
});
