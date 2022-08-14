import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { OrderTypesService } from '../service/order-types.service';
import { IOrderTypes, OrderTypes } from '../order-types.model';

import { OrderTypesUpdateComponent } from './order-types-update.component';

describe('OrderTypes Management Update Component', () => {
  let comp: OrderTypesUpdateComponent;
  let fixture: ComponentFixture<OrderTypesUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let orderTypesService: OrderTypesService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [OrderTypesUpdateComponent],
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
      .overrideTemplate(OrderTypesUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(OrderTypesUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    orderTypesService = TestBed.inject(OrderTypesService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const orderTypes: IOrderTypes = { id: 456 };

      activatedRoute.data = of({ orderTypes });
      comp.ngOnInit();

      expect(comp.editForm.value).toEqual(expect.objectContaining(orderTypes));
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<OrderTypes>>();
      const orderTypes = { id: 123 };
      jest.spyOn(orderTypesService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ orderTypes });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: orderTypes }));
      saveSubject.complete();

      // THEN
      expect(comp.previousState).toHaveBeenCalled();
      expect(orderTypesService.update).toHaveBeenCalledWith(orderTypes);
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<OrderTypes>>();
      const orderTypes = new OrderTypes();
      jest.spyOn(orderTypesService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ orderTypes });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: orderTypes }));
      saveSubject.complete();

      // THEN
      expect(orderTypesService.create).toHaveBeenCalledWith(orderTypes);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<OrderTypes>>();
      const orderTypes = { id: 123 };
      jest.spyOn(orderTypesService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ orderTypes });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(orderTypesService.update).toHaveBeenCalledWith(orderTypes);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
