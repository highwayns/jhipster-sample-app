import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, ActivatedRoute, Router, convertToParamMap } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { IHistoryActions, HistoryActions } from '../history-actions.model';
import { HistoryActionsService } from '../service/history-actions.service';

import { HistoryActionsRoutingResolveService } from './history-actions-routing-resolve.service';

describe('HistoryActions routing resolve service', () => {
  let mockRouter: Router;
  let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
  let routingResolveService: HistoryActionsRoutingResolveService;
  let service: HistoryActionsService;
  let resultHistoryActions: IHistoryActions | undefined;

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
    routingResolveService = TestBed.inject(HistoryActionsRoutingResolveService);
    service = TestBed.inject(HistoryActionsService);
    resultHistoryActions = undefined;
  });

  describe('resolve', () => {
    it('should return IHistoryActions returned by find', () => {
      // GIVEN
      service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultHistoryActions = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultHistoryActions).toEqual({ id: 123 });
    });

    it('should return new IHistoryActions if id is not provided', () => {
      // GIVEN
      service.find = jest.fn();
      mockActivatedRouteSnapshot.params = {};

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultHistoryActions = result;
      });

      // THEN
      expect(service.find).not.toBeCalled();
      expect(resultHistoryActions).toEqual(new HistoryActions());
    });

    it('should route to 404 page if data not found in server', () => {
      // GIVEN
      jest.spyOn(service, 'find').mockReturnValue(of(new HttpResponse({ body: null as unknown as HistoryActions })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultHistoryActions = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultHistoryActions).toEqual(undefined);
      expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
    });
  });
});
