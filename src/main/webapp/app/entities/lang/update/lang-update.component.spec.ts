import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { LangService } from '../service/lang.service';
import { ILang, Lang } from '../lang.model';

import { LangUpdateComponent } from './lang-update.component';

describe('Lang Management Update Component', () => {
  let comp: LangUpdateComponent;
  let fixture: ComponentFixture<LangUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let langService: LangService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [LangUpdateComponent],
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
      .overrideTemplate(LangUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(LangUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    langService = TestBed.inject(LangService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const lang: ILang = { id: 456 };

      activatedRoute.data = of({ lang });
      comp.ngOnInit();

      expect(comp.editForm.value).toEqual(expect.objectContaining(lang));
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Lang>>();
      const lang = { id: 123 };
      jest.spyOn(langService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ lang });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: lang }));
      saveSubject.complete();

      // THEN
      expect(comp.previousState).toHaveBeenCalled();
      expect(langService.update).toHaveBeenCalledWith(lang);
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Lang>>();
      const lang = new Lang();
      jest.spyOn(langService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ lang });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: lang }));
      saveSubject.complete();

      // THEN
      expect(langService.create).toHaveBeenCalledWith(lang);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Lang>>();
      const lang = { id: 123 };
      jest.spyOn(langService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ lang });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(langService.update).toHaveBeenCalledWith(lang);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
