import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, ActivatedRoute, Router, convertToParamMap } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { ICardTokenData, CardTokenData } from '../card-token-data.model';
import { CardTokenDataService } from '../service/card-token-data.service';

import { CardTokenDataRoutingResolveService } from './card-token-data-routing-resolve.service';

describe('CardTokenData routing resolve service', () => {
  let mockRouter: Router;
  let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
  let routingResolveService: CardTokenDataRoutingResolveService;
  let service: CardTokenDataService;
  let resultCardTokenData: ICardTokenData | undefined;

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
    routingResolveService = TestBed.inject(CardTokenDataRoutingResolveService);
    service = TestBed.inject(CardTokenDataService);
    resultCardTokenData = undefined;
  });

  describe('resolve', () => {
    it('should return ICardTokenData returned by find', () => {
      // GIVEN
      service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultCardTokenData = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultCardTokenData).toEqual({ id: 123 });
    });

    it('should return new ICardTokenData if id is not provided', () => {
      // GIVEN
      service.find = jest.fn();
      mockActivatedRouteSnapshot.params = {};

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultCardTokenData = result;
      });

      // THEN
      expect(service.find).not.toBeCalled();
      expect(resultCardTokenData).toEqual(new CardTokenData());
    });

    it('should route to 404 page if data not found in server', () => {
      // GIVEN
      jest.spyOn(service, 'find').mockReturnValue(of(new HttpResponse({ body: null as unknown as CardTokenData })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultCardTokenData = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultCardTokenData).toEqual(undefined);
      expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
    });
  });
});
