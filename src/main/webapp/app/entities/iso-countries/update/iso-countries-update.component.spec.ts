import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { IsoCountriesService } from '../service/iso-countries.service';
import { IIsoCountries, IsoCountries } from '../iso-countries.model';

import { IsoCountriesUpdateComponent } from './iso-countries-update.component';

describe('IsoCountries Management Update Component', () => {
  let comp: IsoCountriesUpdateComponent;
  let fixture: ComponentFixture<IsoCountriesUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let isoCountriesService: IsoCountriesService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [IsoCountriesUpdateComponent],
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
      .overrideTemplate(IsoCountriesUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(IsoCountriesUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    isoCountriesService = TestBed.inject(IsoCountriesService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const isoCountries: IIsoCountries = { id: 456 };

      activatedRoute.data = of({ isoCountries });
      comp.ngOnInit();

      expect(comp.editForm.value).toEqual(expect.objectContaining(isoCountries));
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IsoCountries>>();
      const isoCountries = { id: 123 };
      jest.spyOn(isoCountriesService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ isoCountries });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: isoCountries }));
      saveSubject.complete();

      // THEN
      expect(comp.previousState).toHaveBeenCalled();
      expect(isoCountriesService.update).toHaveBeenCalledWith(isoCountries);
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IsoCountries>>();
      const isoCountries = new IsoCountries();
      jest.spyOn(isoCountriesService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ isoCountries });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: isoCountries }));
      saveSubject.complete();

      // THEN
      expect(isoCountriesService.create).toHaveBeenCalledWith(isoCountries);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IsoCountries>>();
      const isoCountries = { id: 123 };
      jest.spyOn(isoCountriesService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ isoCountries });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(isoCountriesService.update).toHaveBeenCalledWith(isoCountries);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
