import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, ActivatedRoute, Router, convertToParamMap } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { IMonthlyReports, MonthlyReports } from '../monthly-reports.model';
import { MonthlyReportsService } from '../service/monthly-reports.service';

import { MonthlyReportsRoutingResolveService } from './monthly-reports-routing-resolve.service';

describe('MonthlyReports routing resolve service', () => {
  let mockRouter: Router;
  let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
  let routingResolveService: MonthlyReportsRoutingResolveService;
  let service: MonthlyReportsService;
  let resultMonthlyReports: IMonthlyReports | undefined;

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
    routingResolveService = TestBed.inject(MonthlyReportsRoutingResolveService);
    service = TestBed.inject(MonthlyReportsService);
    resultMonthlyReports = undefined;
  });

  describe('resolve', () => {
    it('should return IMonthlyReports returned by find', () => {
      // GIVEN
      service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultMonthlyReports = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultMonthlyReports).toEqual({ id: 123 });
    });

    it('should return new IMonthlyReports if id is not provided', () => {
      // GIVEN
      service.find = jest.fn();
      mockActivatedRouteSnapshot.params = {};

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultMonthlyReports = result;
      });

      // THEN
      expect(service.find).not.toBeCalled();
      expect(resultMonthlyReports).toEqual(new MonthlyReports());
    });

    it('should route to 404 page if data not found in server', () => {
      // GIVEN
      jest.spyOn(service, 'find').mockReturnValue(of(new HttpResponse({ body: null as unknown as MonthlyReports })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultMonthlyReports = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultMonthlyReports).toEqual(undefined);
      expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
    });
  });
});
