import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { LinksService } from '../service/links.service';
import { ILinks, Links } from '../links.model';
import { ILink } from 'app/entities/link/link.model';
import { LinkService } from 'app/entities/link/service/link.service';

import { LinksUpdateComponent } from './links-update.component';

describe('Links Management Update Component', () => {
  let comp: LinksUpdateComponent;
  let fixture: ComponentFixture<LinksUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let linksService: LinksService;
  let linkService: LinkService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [LinksUpdateComponent],
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
      .overrideTemplate(LinksUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(LinksUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    linksService = TestBed.inject(LinksService);
    linkService = TestBed.inject(LinkService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call data query and add missing value', () => {
      const links: ILinks = { id: 456 };
      const data: ILink = { id: 94377 };
      links.data = data;

      const dataCollection: ILink[] = [{ id: 4008 }];
      jest.spyOn(linkService, 'query').mockReturnValue(of(new HttpResponse({ body: dataCollection })));
      const expectedCollection: ILink[] = [data, ...dataCollection];
      jest.spyOn(linkService, 'addLinkToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ links });
      comp.ngOnInit();

      expect(linkService.query).toHaveBeenCalled();
      expect(linkService.addLinkToCollectionIfMissing).toHaveBeenCalledWith(dataCollection, data);
      expect(comp.dataCollection).toEqual(expectedCollection);
    });

    it('Should call action query and add missing value', () => {
      const links: ILinks = { id: 456 };
      const action: ILink = { id: 73110 };
      links.action = action;

      const actionCollection: ILink[] = [{ id: 41411 }];
      jest.spyOn(linkService, 'query').mockReturnValue(of(new HttpResponse({ body: actionCollection })));
      const expectedCollection: ILink[] = [action, ...actionCollection];
      jest.spyOn(linkService, 'addLinkToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ links });
      comp.ngOnInit();

      expect(linkService.query).toHaveBeenCalled();
      expect(linkService.addLinkToCollectionIfMissing).toHaveBeenCalledWith(actionCollection, action);
      expect(comp.actionsCollection).toEqual(expectedCollection);
    });

    it('Should call documentation query and add missing value', () => {
      const links: ILinks = { id: 456 };
      const documentation: ILink = { id: 58486 };
      links.documentation = documentation;

      const documentationCollection: ILink[] = [{ id: 99193 }];
      jest.spyOn(linkService, 'query').mockReturnValue(of(new HttpResponse({ body: documentationCollection })));
      const expectedCollection: ILink[] = [documentation, ...documentationCollection];
      jest.spyOn(linkService, 'addLinkToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ links });
      comp.ngOnInit();

      expect(linkService.query).toHaveBeenCalled();
      expect(linkService.addLinkToCollectionIfMissing).toHaveBeenCalledWith(documentationCollection, documentation);
      expect(comp.documentationsCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const links: ILinks = { id: 456 };
      const data: ILink = { id: 74577 };
      links.data = data;
      const action: ILink = { id: 71369 };
      links.action = action;
      const documentation: ILink = { id: 95066 };
      links.documentation = documentation;

      activatedRoute.data = of({ links });
      comp.ngOnInit();

      expect(comp.editForm.value).toEqual(expect.objectContaining(links));
      expect(comp.dataCollection).toContain(data);
      expect(comp.actionsCollection).toContain(action);
      expect(comp.documentationsCollection).toContain(documentation);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Links>>();
      const links = { id: 123 };
      jest.spyOn(linksService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ links });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: links }));
      saveSubject.complete();

      // THEN
      expect(comp.previousState).toHaveBeenCalled();
      expect(linksService.update).toHaveBeenCalledWith(links);
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Links>>();
      const links = new Links();
      jest.spyOn(linksService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ links });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: links }));
      saveSubject.complete();

      // THEN
      expect(linksService.create).toHaveBeenCalledWith(links);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Links>>();
      const links = { id: 123 };
      jest.spyOn(linksService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ links });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(linksService.update).toHaveBeenCalledWith(links);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Tracking relationships identifiers', () => {
    describe('trackLinkById', () => {
      it('Should return tracked Link primary key', () => {
        const entity = { id: 123 };
        const trackResult = comp.trackLinkById(0, entity);
        expect(trackResult).toEqual(entity.id);
      });
    });
  });
});
