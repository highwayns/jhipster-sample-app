import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, ActivatedRoute, Router, convertToParamMap } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { IPaymentAttributes, PaymentAttributes } from '../payment-attributes.model';
import { PaymentAttributesService } from '../service/payment-attributes.service';

import { PaymentAttributesRoutingResolveService } from './payment-attributes-routing-resolve.service';

describe('PaymentAttributes routing resolve service', () => {
  let mockRouter: Router;
  let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
  let routingResolveService: PaymentAttributesRoutingResolveService;
  let service: PaymentAttributesService;
  let resultPaymentAttributes: IPaymentAttributes | undefined;

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
    routingResolveService = TestBed.inject(PaymentAttributesRoutingResolveService);
    service = TestBed.inject(PaymentAttributesService);
    resultPaymentAttributes = undefined;
  });

  describe('resolve', () => {
    it('should return IPaymentAttributes returned by find', () => {
      // GIVEN
      service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultPaymentAttributes = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultPaymentAttributes).toEqual({ id: 123 });
    });

    it('should return new IPaymentAttributes if id is not provided', () => {
      // GIVEN
      service.find = jest.fn();
      mockActivatedRouteSnapshot.params = {};

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultPaymentAttributes = result;
      });

      // THEN
      expect(service.find).not.toBeCalled();
      expect(resultPaymentAttributes).toEqual(new PaymentAttributes());
    });

    it('should route to 404 page if data not found in server', () => {
      // GIVEN
      jest.spyOn(service, 'find').mockReturnValue(of(new HttpResponse({ body: null as unknown as PaymentAttributes })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultPaymentAttributes = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultPaymentAttributes).toEqual(undefined);
      expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
    });
  });
});
