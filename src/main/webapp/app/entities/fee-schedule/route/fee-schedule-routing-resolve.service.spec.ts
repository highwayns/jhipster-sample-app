import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, ActivatedRoute, Router, convertToParamMap } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { IFeeSchedule, FeeSchedule } from '../fee-schedule.model';
import { FeeScheduleService } from '../service/fee-schedule.service';

import { FeeScheduleRoutingResolveService } from './fee-schedule-routing-resolve.service';

describe('FeeSchedule routing resolve service', () => {
  let mockRouter: Router;
  let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
  let routingResolveService: FeeScheduleRoutingResolveService;
  let service: FeeScheduleService;
  let resultFeeSchedule: IFeeSchedule | undefined;

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
    routingResolveService = TestBed.inject(FeeScheduleRoutingResolveService);
    service = TestBed.inject(FeeScheduleService);
    resultFeeSchedule = undefined;
  });

  describe('resolve', () => {
    it('should return IFeeSchedule returned by find', () => {
      // GIVEN
      service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultFeeSchedule = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultFeeSchedule).toEqual({ id: 123 });
    });

    it('should return new IFeeSchedule if id is not provided', () => {
      // GIVEN
      service.find = jest.fn();
      mockActivatedRouteSnapshot.params = {};

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultFeeSchedule = result;
      });

      // THEN
      expect(service.find).not.toBeCalled();
      expect(resultFeeSchedule).toEqual(new FeeSchedule());
    });

    it('should route to 404 page if data not found in server', () => {
      // GIVEN
      jest.spyOn(service, 'find').mockReturnValue(of(new HttpResponse({ body: null as unknown as FeeSchedule })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultFeeSchedule = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultFeeSchedule).toEqual(undefined);
      expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
    });
  });
});
