import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { CurrencysService } from '../service/currencys.service';
import { ICurrencys, Currencys } from '../currencys.model';

import { CurrencysUpdateComponent } from './currencys-update.component';

describe('Currencys Management Update Component', () => {
  let comp: CurrencysUpdateComponent;
  let fixture: ComponentFixture<CurrencysUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let currencysService: CurrencysService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [CurrencysUpdateComponent],
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
      .overrideTemplate(CurrencysUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(CurrencysUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    currencysService = TestBed.inject(CurrencysService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const currencys: ICurrencys = { id: 456 };

      activatedRoute.data = of({ currencys });
      comp.ngOnInit();

      expect(comp.editForm.value).toEqual(expect.objectContaining(currencys));
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Currencys>>();
      const currencys = { id: 123 };
      jest.spyOn(currencysService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ currencys });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: currencys }));
      saveSubject.complete();

      // THEN
      expect(comp.previousState).toHaveBeenCalled();
      expect(currencysService.update).toHaveBeenCalledWith(currencys);
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Currencys>>();
      const currencys = new Currencys();
      jest.spyOn(currencysService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ currencys });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: currencys }));
      saveSubject.complete();

      // THEN
      expect(currencysService.create).toHaveBeenCalledWith(currencys);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Currencys>>();
      const currencys = { id: 123 };
      jest.spyOn(currencysService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ currencys });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(currencysService.update).toHaveBeenCalledWith(currencys);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
