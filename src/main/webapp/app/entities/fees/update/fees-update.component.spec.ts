import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { FeesService } from '../service/fees.service';
import { IFees, Fees } from '../fees.model';

import { FeesUpdateComponent } from './fees-update.component';

describe('Fees Management Update Component', () => {
  let comp: FeesUpdateComponent;
  let fixture: ComponentFixture<FeesUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let feesService: FeesService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [FeesUpdateComponent],
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
      .overrideTemplate(FeesUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(FeesUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    feesService = TestBed.inject(FeesService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const fees: IFees = { id: 456 };

      activatedRoute.data = of({ fees });
      comp.ngOnInit();

      expect(comp.editForm.value).toEqual(expect.objectContaining(fees));
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Fees>>();
      const fees = { id: 123 };
      jest.spyOn(feesService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ fees });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: fees }));
      saveSubject.complete();

      // THEN
      expect(comp.previousState).toHaveBeenCalled();
      expect(feesService.update).toHaveBeenCalledWith(fees);
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Fees>>();
      const fees = new Fees();
      jest.spyOn(feesService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ fees });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: fees }));
      saveSubject.complete();

      // THEN
      expect(feesService.create).toHaveBeenCalledWith(fees);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Fees>>();
      const fees = { id: 123 };
      jest.spyOn(feesService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ fees });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(feesService.update).toHaveBeenCalledWith(fees);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
