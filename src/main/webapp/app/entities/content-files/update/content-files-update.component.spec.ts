import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { ContentFilesService } from '../service/content-files.service';
import { IContentFiles, ContentFiles } from '../content-files.model';

import { ContentFilesUpdateComponent } from './content-files-update.component';

describe('ContentFiles Management Update Component', () => {
  let comp: ContentFilesUpdateComponent;
  let fixture: ComponentFixture<ContentFilesUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let contentFilesService: ContentFilesService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [ContentFilesUpdateComponent],
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
      .overrideTemplate(ContentFilesUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(ContentFilesUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    contentFilesService = TestBed.inject(ContentFilesService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const contentFiles: IContentFiles = { id: 456 };

      activatedRoute.data = of({ contentFiles });
      comp.ngOnInit();

      expect(comp.editForm.value).toEqual(expect.objectContaining(contentFiles));
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ContentFiles>>();
      const contentFiles = { id: 123 };
      jest.spyOn(contentFilesService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ contentFiles });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: contentFiles }));
      saveSubject.complete();

      // THEN
      expect(comp.previousState).toHaveBeenCalled();
      expect(contentFilesService.update).toHaveBeenCalledWith(contentFiles);
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ContentFiles>>();
      const contentFiles = new ContentFiles();
      jest.spyOn(contentFilesService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ contentFiles });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: contentFiles }));
      saveSubject.complete();

      // THEN
      expect(contentFilesService.create).toHaveBeenCalledWith(contentFiles);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ContentFiles>>();
      const contentFiles = { id: 123 };
      jest.spyOn(contentFilesService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ contentFiles });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(contentFilesService.update).toHaveBeenCalledWith(contentFiles);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
