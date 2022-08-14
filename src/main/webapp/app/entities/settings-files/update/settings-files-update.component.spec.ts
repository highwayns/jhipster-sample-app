import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { SettingsFilesService } from '../service/settings-files.service';
import { ISettingsFiles, SettingsFiles } from '../settings-files.model';

import { SettingsFilesUpdateComponent } from './settings-files-update.component';

describe('SettingsFiles Management Update Component', () => {
  let comp: SettingsFilesUpdateComponent;
  let fixture: ComponentFixture<SettingsFilesUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let settingsFilesService: SettingsFilesService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [SettingsFilesUpdateComponent],
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
      .overrideTemplate(SettingsFilesUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(SettingsFilesUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    settingsFilesService = TestBed.inject(SettingsFilesService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const settingsFiles: ISettingsFiles = { id: 456 };

      activatedRoute.data = of({ settingsFiles });
      comp.ngOnInit();

      expect(comp.editForm.value).toEqual(expect.objectContaining(settingsFiles));
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<SettingsFiles>>();
      const settingsFiles = { id: 123 };
      jest.spyOn(settingsFilesService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ settingsFiles });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: settingsFiles }));
      saveSubject.complete();

      // THEN
      expect(comp.previousState).toHaveBeenCalled();
      expect(settingsFilesService.update).toHaveBeenCalledWith(settingsFiles);
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<SettingsFiles>>();
      const settingsFiles = new SettingsFiles();
      jest.spyOn(settingsFilesService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ settingsFiles });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: settingsFiles }));
      saveSubject.complete();

      // THEN
      expect(settingsFilesService.create).toHaveBeenCalledWith(settingsFiles);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<SettingsFiles>>();
      const settingsFiles = { id: 123 };
      jest.spyOn(settingsFilesService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ settingsFiles });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(settingsFilesService.update).toHaveBeenCalledWith(settingsFiles);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
