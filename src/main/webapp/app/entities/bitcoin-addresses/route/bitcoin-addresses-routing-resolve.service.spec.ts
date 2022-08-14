import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, ActivatedRoute, Router, convertToParamMap } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { IBitcoinAddresses, BitcoinAddresses } from '../bitcoin-addresses.model';
import { BitcoinAddressesService } from '../service/bitcoin-addresses.service';

import { BitcoinAddressesRoutingResolveService } from './bitcoin-addresses-routing-resolve.service';

describe('BitcoinAddresses routing resolve service', () => {
  let mockRouter: Router;
  let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
  let routingResolveService: BitcoinAddressesRoutingResolveService;
  let service: BitcoinAddressesService;
  let resultBitcoinAddresses: IBitcoinAddresses | undefined;

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
    routingResolveService = TestBed.inject(BitcoinAddressesRoutingResolveService);
    service = TestBed.inject(BitcoinAddressesService);
    resultBitcoinAddresses = undefined;
  });

  describe('resolve', () => {
    it('should return IBitcoinAddresses returned by find', () => {
      // GIVEN
      service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultBitcoinAddresses = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultBitcoinAddresses).toEqual({ id: 123 });
    });

    it('should return new IBitcoinAddresses if id is not provided', () => {
      // GIVEN
      service.find = jest.fn();
      mockActivatedRouteSnapshot.params = {};

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultBitcoinAddresses = result;
      });

      // THEN
      expect(service.find).not.toBeCalled();
      expect(resultBitcoinAddresses).toEqual(new BitcoinAddresses());
    });

    it('should route to 404 page if data not found in server', () => {
      // GIVEN
      jest.spyOn(service, 'find').mockReturnValue(of(new HttpResponse({ body: null as unknown as BitcoinAddresses })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultBitcoinAddresses = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultBitcoinAddresses).toEqual(undefined);
      expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
    });
  });
});
