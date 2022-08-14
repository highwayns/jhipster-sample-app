import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, ActivatedRoute, Router, convertToParamMap } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { IStatusEscrows, StatusEscrows } from '../status-escrows.model';
import { StatusEscrowsService } from '../service/status-escrows.service';

import { StatusEscrowsRoutingResolveService } from './status-escrows-routing-resolve.service';

describe('StatusEscrows routing resolve service', () => {
  let mockRouter: Router;
  let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
  let routingResolveService: StatusEscrowsRoutingResolveService;
  let service: StatusEscrowsService;
  let resultStatusEscrows: IStatusEscrows | undefined;

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
    routingResolveService = TestBed.inject(StatusEscrowsRoutingResolveService);
    service = TestBed.inject(StatusEscrowsService);
    resultStatusEscrows = undefined;
  });

  describe('resolve', () => {
    it('should return IStatusEscrows returned by find', () => {
      // GIVEN
      service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultStatusEscrows = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultStatusEscrows).toEqual({ id: 123 });
    });

    it('should return new IStatusEscrows if id is not provided', () => {
      // GIVEN
      service.find = jest.fn();
      mockActivatedRouteSnapshot.params = {};

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultStatusEscrows = result;
      });

      // THEN
      expect(service.find).not.toBeCalled();
      expect(resultStatusEscrows).toEqual(new StatusEscrows());
    });

    it('should route to 404 page if data not found in server', () => {
      // GIVEN
      jest.spyOn(service, 'find').mockReturnValue(of(new HttpResponse({ body: null as unknown as StatusEscrows })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultStatusEscrows = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultStatusEscrows).toEqual(undefined);
      expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
    });
  });
});
