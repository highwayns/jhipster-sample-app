import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, ActivatedRoute, Router, convertToParamMap } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { IBitcoindLog, BitcoindLog } from '../bitcoind-log.model';
import { BitcoindLogService } from '../service/bitcoind-log.service';

import { BitcoindLogRoutingResolveService } from './bitcoind-log-routing-resolve.service';

describe('BitcoindLog routing resolve service', () => {
  let mockRouter: Router;
  let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
  let routingResolveService: BitcoindLogRoutingResolveService;
  let service: BitcoindLogService;
  let resultBitcoindLog: IBitcoindLog | undefined;

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
    routingResolveService = TestBed.inject(BitcoindLogRoutingResolveService);
    service = TestBed.inject(BitcoindLogService);
    resultBitcoindLog = undefined;
  });

  describe('resolve', () => {
    it('should return IBitcoindLog returned by find', () => {
      // GIVEN
      service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultBitcoindLog = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultBitcoindLog).toEqual({ id: 123 });
    });

    it('should return new IBitcoindLog if id is not provided', () => {
      // GIVEN
      service.find = jest.fn();
      mockActivatedRouteSnapshot.params = {};

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultBitcoindLog = result;
      });

      // THEN
      expect(service.find).not.toBeCalled();
      expect(resultBitcoindLog).toEqual(new BitcoindLog());
    });

    it('should route to 404 page if data not found in server', () => {
      // GIVEN
      jest.spyOn(service, 'find').mockReturnValue(of(new HttpResponse({ body: null as unknown as BitcoindLog })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultBitcoindLog = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultBitcoindLog).toEqual(undefined);
      expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
    });
  });
});
