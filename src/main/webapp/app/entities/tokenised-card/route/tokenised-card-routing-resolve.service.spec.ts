import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, ActivatedRoute, Router, convertToParamMap } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { ITokenisedCard, TokenisedCard } from '../tokenised-card.model';
import { TokenisedCardService } from '../service/tokenised-card.service';

import { TokenisedCardRoutingResolveService } from './tokenised-card-routing-resolve.service';

describe('TokenisedCard routing resolve service', () => {
  let mockRouter: Router;
  let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
  let routingResolveService: TokenisedCardRoutingResolveService;
  let service: TokenisedCardService;
  let resultTokenisedCard: ITokenisedCard | undefined;

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
    routingResolveService = TestBed.inject(TokenisedCardRoutingResolveService);
    service = TestBed.inject(TokenisedCardService);
    resultTokenisedCard = undefined;
  });

  describe('resolve', () => {
    it('should return ITokenisedCard returned by find', () => {
      // GIVEN
      service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultTokenisedCard = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultTokenisedCard).toEqual({ id: 123 });
    });

    it('should return new ITokenisedCard if id is not provided', () => {
      // GIVEN
      service.find = jest.fn();
      mockActivatedRouteSnapshot.params = {};

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultTokenisedCard = result;
      });

      // THEN
      expect(service.find).not.toBeCalled();
      expect(resultTokenisedCard).toEqual(new TokenisedCard());
    });

    it('should route to 404 page if data not found in server', () => {
      // GIVEN
      jest.spyOn(service, 'find').mockReturnValue(of(new HttpResponse({ body: null as unknown as TokenisedCard })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultTokenisedCard = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultTokenisedCard).toEqual(undefined);
      expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
    });
  });
});
