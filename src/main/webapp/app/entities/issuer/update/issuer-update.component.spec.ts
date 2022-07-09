import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { IssuerService } from '../service/issuer.service';
import { IIssuer, Issuer } from '../issuer.model';

import { IssuerUpdateComponent } from './issuer-update.component';

describe('Issuer Management Update Component', () => {
  let comp: IssuerUpdateComponent;
  let fixture: ComponentFixture<IssuerUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let issuerService: IssuerService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [IssuerUpdateComponent],
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
      .overrideTemplate(IssuerUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(IssuerUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    issuerService = TestBed.inject(IssuerService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const issuer: IIssuer = { id: 'CBA' };

      activatedRoute.data = of({ issuer });
      comp.ngOnInit();

      expect(comp.editForm.value).toEqual(expect.objectContaining(issuer));
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Issuer>>();
      const issuer = { id: 'ABC' };
      jest.spyOn(issuerService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ issuer });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: issuer }));
      saveSubject.complete();

      // THEN
      expect(comp.previousState).toHaveBeenCalled();
      expect(issuerService.update).toHaveBeenCalledWith(issuer);
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Issuer>>();
      const issuer = new Issuer();
      jest.spyOn(issuerService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ issuer });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: issuer }));
      saveSubject.complete();

      // THEN
      expect(issuerService.create).toHaveBeenCalledWith(issuer);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Issuer>>();
      const issuer = { id: 'ABC' };
      jest.spyOn(issuerService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ issuer });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(issuerService.update).toHaveBeenCalledWith(issuer);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
