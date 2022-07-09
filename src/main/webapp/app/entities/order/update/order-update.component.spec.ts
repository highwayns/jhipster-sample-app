import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { OrderService } from '../service/order.service';
import { IOrder, Order } from '../order.model';
import { IAddress } from 'app/entities/address/address.model';
import { AddressService } from 'app/entities/address/service/address.service';
import { IIdentity } from 'app/entities/identity/identity.model';
import { IdentityService } from 'app/entities/identity/service/identity.service';
import { IOrderLine } from 'app/entities/order-line/order-line.model';
import { OrderLineService } from 'app/entities/order-line/service/order-line.service';

import { OrderUpdateComponent } from './order-update.component';

describe('Order Management Update Component', () => {
  let comp: OrderUpdateComponent;
  let fixture: ComponentFixture<OrderUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let orderService: OrderService;
  let addressService: AddressService;
  let identityService: IdentityService;
  let orderLineService: OrderLineService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [OrderUpdateComponent],
      providers: [
        FormBuilder,
        {
          provide: ActivatedRoute,
          useValue: {
            params: from([{}]),
          },
        },
      ],
    })
      .overrideTemplate(OrderUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(OrderUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    orderService = TestBed.inject(OrderService);
    addressService = TestBed.inject(AddressService);
    identityService = TestBed.inject(IdentityService);
    orderLineService = TestBed.inject(OrderLineService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call billingAddress query and add missing value', () => {
      const order: IOrder = { id: 456 };
      const billingAddress: IAddress = { id: 83914 };
      order.billingAddress = billingAddress;

      const billingAddressCollection: IAddress[] = [{ id: 94326 }];
      jest.spyOn(addressService, 'query').mockReturnValue(of(new HttpResponse({ body: billingAddressCollection })));
      const expectedCollection: IAddress[] = [billingAddress, ...billingAddressCollection];
      jest.spyOn(addressService, 'addAddressToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ order });
      comp.ngOnInit();

      expect(addressService.query).toHaveBeenCalled();
      expect(addressService.addAddressToCollectionIfMissing).toHaveBeenCalledWith(billingAddressCollection, billingAddress);
      expect(comp.billingAddressesCollection).toEqual(expectedCollection);
    });

    it('Should call shippingAddress query and add missing value', () => {
      const order: IOrder = { id: 456 };
      const shippingAddress: IAddress = { id: 39703 };
      order.shippingAddress = shippingAddress;

      const shippingAddressCollection: IAddress[] = [{ id: 34327 }];
      jest.spyOn(addressService, 'query').mockReturnValue(of(new HttpResponse({ body: shippingAddressCollection })));
      const expectedCollection: IAddress[] = [shippingAddress, ...shippingAddressCollection];
      jest.spyOn(addressService, 'addAddressToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ order });
      comp.ngOnInit();

      expect(addressService.query).toHaveBeenCalled();
      expect(addressService.addAddressToCollectionIfMissing).toHaveBeenCalledWith(shippingAddressCollection, shippingAddress);
      expect(comp.shippingAddressesCollection).toEqual(expectedCollection);
    });

    it('Should call billingIdentity query and add missing value', () => {
      const order: IOrder = { id: 456 };
      const billingIdentity: IIdentity = { id: 45198 };
      order.billingIdentity = billingIdentity;

      const billingIdentityCollection: IIdentity[] = [{ id: 12346 }];
      jest.spyOn(identityService, 'query').mockReturnValue(of(new HttpResponse({ body: billingIdentityCollection })));
      const expectedCollection: IIdentity[] = [billingIdentity, ...billingIdentityCollection];
      jest.spyOn(identityService, 'addIdentityToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ order });
      comp.ngOnInit();

      expect(identityService.query).toHaveBeenCalled();
      expect(identityService.addIdentityToCollectionIfMissing).toHaveBeenCalledWith(billingIdentityCollection, billingIdentity);
      expect(comp.billingIdentitiesCollection).toEqual(expectedCollection);
    });

    it('Should call OrderLine query and add missing value', () => {
      const order: IOrder = { id: 456 };
      const orderLines: IOrderLine = { id: 84748 };
      order.orderLines = orderLines;

      const orderLineCollection: IOrderLine[] = [{ id: 84616 }];
      jest.spyOn(orderLineService, 'query').mockReturnValue(of(new HttpResponse({ body: orderLineCollection })));
      const additionalOrderLines = [orderLines];
      const expectedCollection: IOrderLine[] = [...additionalOrderLines, ...orderLineCollection];
      jest.spyOn(orderLineService, 'addOrderLineToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ order });
      comp.ngOnInit();

      expect(orderLineService.query).toHaveBeenCalled();
      expect(orderLineService.addOrderLineToCollectionIfMissing).toHaveBeenCalledWith(orderLineCollection, ...additionalOrderLines);
      expect(comp.orderLinesSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const order: IOrder = { id: 456 };
      const billingAddress: IAddress = { id: 63820 };
      order.billingAddress = billingAddress;
      const shippingAddress: IAddress = { id: 68345 };
      order.shippingAddress = shippingAddress;
      const billingIdentity: IIdentity = { id: 47580 };
      order.billingIdentity = billingIdentity;
      const orderLines: IOrderLine = { id: 16705 };
      order.orderLines = orderLines;

      activatedRoute.data = of({ order });
      comp.ngOnInit();

      expect(comp.editForm.value).toEqual(expect.objectContaining(order));
      expect(comp.billingAddressesCollection).toContain(billingAddress);
      expect(comp.shippingAddressesCollection).toContain(shippingAddress);
      expect(comp.billingIdentitiesCollection).toContain(billingIdentity);
      expect(comp.orderLinesSharedCollection).toContain(orderLines);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Order>>();
      const order = { id: 123 };
      jest.spyOn(orderService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ order });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: order }));
      saveSubject.complete();

      // THEN
      expect(comp.previousState).toHaveBeenCalled();
      expect(orderService.update).toHaveBeenCalledWith(order);
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Order>>();
      const order = new Order();
      jest.spyOn(orderService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ order });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: order }));
      saveSubject.complete();

      // THEN
      expect(orderService.create).toHaveBeenCalledWith(order);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Order>>();
      const order = { id: 123 };
      jest.spyOn(orderService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ order });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(orderService.update).toHaveBeenCalledWith(order);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Tracking relationships identifiers', () => {
    describe('trackAddressById', () => {
      it('Should return tracked Address primary key', () => {
        const entity = { id: 123 };
        const trackResult = comp.trackAddressById(0, entity);
        expect(trackResult).toEqual(entity.id);
      });
    });

    describe('trackIdentityById', () => {
      it('Should return tracked Identity primary key', () => {
        const entity = { id: 123 };
        const trackResult = comp.trackIdentityById(0, entity);
        expect(trackResult).toEqual(entity.id);
      });
    });

    describe('trackOrderLineById', () => {
      it('Should return tracked OrderLine primary key', () => {
        const entity = { id: 123 };
        const trackResult = comp.trackOrderLineById(0, entity);
        expect(trackResult).toEqual(entity.id);
      });
    });
  });
});
