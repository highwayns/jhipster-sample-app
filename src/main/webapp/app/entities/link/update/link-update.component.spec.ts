import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { LinkService } from '../service/link.service';
import { ILink, Link } from '../link.model';

import { LinkUpdateComponent } from './link-update.component';

describe('Link Management Update Component', () => {
  let comp: LinkUpdateComponent;
  let fixture: ComponentFixture<LinkUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let linkService: LinkService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [LinkUpdateComponent],
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
      .overrideTemplate(LinkUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(LinkUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    linkService = TestBed.inject(LinkService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const link: ILink = { id: 456 };

      activatedRoute.data = of({ link });
      comp.ngOnInit();

      expect(comp.editForm.value).toEqual(expect.objectContaining(link));
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Link>>();
      const link = { id: 123 };
      jest.spyOn(linkService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ link });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: link }));
      saveSubject.complete();

      // THEN
      expect(comp.previousState).toHaveBeenCalled();
      expect(linkService.update).toHaveBeenCalledWith(link);
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Link>>();
      const link = new Link();
      jest.spyOn(linkService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ link });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: link }));
      saveSubject.complete();

      // THEN
      expect(linkService.create).toHaveBeenCalledWith(link);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Link>>();
      const link = { id: 123 };
      jest.spyOn(linkService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ link });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(linkService.update).toHaveBeenCalledWith(link);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
