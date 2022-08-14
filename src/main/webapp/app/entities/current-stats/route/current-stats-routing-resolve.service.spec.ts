import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, ActivatedRoute, Router, convertToParamMap } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { ICurrentStats, CurrentStats } from '../current-stats.model';
import { CurrentStatsService } from '../service/current-stats.service';

import { CurrentStatsRoutingResolveService } from './current-stats-routing-resolve.service';

describe('CurrentStats routing resolve service', () => {
  let mockRouter: Router;
  let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
  let routingResolveService: CurrentStatsRoutingResolveService;
  let service: CurrentStatsService;
  let resultCurrentStats: ICurrentStats | undefined;

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
    routingResolveService = TestBed.inject(CurrentStatsRoutingResolveService);
    service = TestBed.inject(CurrentStatsService);
    resultCurrentStats = undefined;
  });

  describe('resolve', () => {
    it('should return ICurrentStats returned by find', () => {
      // GIVEN
      service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultCurrentStats = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultCurrentStats).toEqual({ id: 123 });
    });

    it('should return new ICurrentStats if id is not provided', () => {
      // GIVEN
      service.find = jest.fn();
      mockActivatedRouteSnapshot.params = {};

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultCurrentStats = result;
      });

      // THEN
      expect(service.find).not.toBeCalled();
      expect(resultCurrentStats).toEqual(new CurrentStats());
    });

    it('should route to 404 page if data not found in server', () => {
      // GIVEN
      jest.spyOn(service, 'find').mockReturnValue(of(new HttpResponse({ body: null as unknown as CurrentStats })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultCurrentStats = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultCurrentStats).toEqual(undefined);
      expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
    });
  });
});
