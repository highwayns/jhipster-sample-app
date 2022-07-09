import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, ActivatedRoute, Router, convertToParamMap } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { IPaymentJobAttributes, PaymentJobAttributes } from '../payment-job-attributes.model';
import { PaymentJobAttributesService } from '../service/payment-job-attributes.service';

import { PaymentJobAttributesRoutingResolveService } from './payment-job-attributes-routing-resolve.service';

describe('PaymentJobAttributes routing resolve service', () => {
  let mockRouter: Router;
  let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
  let routingResolveService: PaymentJobAttributesRoutingResolveService;
  let service: PaymentJobAttributesService;
  let resultPaymentJobAttributes: IPaymentJobAttributes | undefined;

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
    routingResolveService = TestBed.inject(PaymentJobAttributesRoutingResolveService);
    service = TestBed.inject(PaymentJobAttributesService);
    resultPaymentJobAttributes = undefined;
  });

  describe('resolve', () => {
    it('should return IPaymentJobAttributes returned by find', () => {
      // GIVEN
      service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultPaymentJobAttributes = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultPaymentJobAttributes).toEqual({ id: 123 });
    });

    it('should return new IPaymentJobAttributes if id is not provided', () => {
      // GIVEN
      service.find = jest.fn();
      mockActivatedRouteSnapshot.params = {};

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultPaymentJobAttributes = result;
      });

      // THEN
      expect(service.find).not.toBeCalled();
      expect(resultPaymentJobAttributes).toEqual(new PaymentJobAttributes());
    });

    it('should route to 404 page if data not found in server', () => {
      // GIVEN
      jest.spyOn(service, 'find').mockReturnValue(of(new HttpResponse({ body: null as unknown as PaymentJobAttributes })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultPaymentJobAttributes = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultPaymentJobAttributes).toEqual(undefined);
      expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
    });
  });
});
