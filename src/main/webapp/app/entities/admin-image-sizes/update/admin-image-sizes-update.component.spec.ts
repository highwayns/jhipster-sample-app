import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { AdminImageSizesService } from '../service/admin-image-sizes.service';
import { IAdminImageSizes, AdminImageSizes } from '../admin-image-sizes.model';

import { AdminImageSizesUpdateComponent } from './admin-image-sizes-update.component';

describe('AdminImageSizes Management Update Component', () => {
  let comp: AdminImageSizesUpdateComponent;
  let fixture: ComponentFixture<AdminImageSizesUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let adminImageSizesService: AdminImageSizesService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [AdminImageSizesUpdateComponent],
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
      .overrideTemplate(AdminImageSizesUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(AdminImageSizesUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    adminImageSizesService = TestBed.inject(AdminImageSizesService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const adminImageSizes: IAdminImageSizes = { id: 456 };

      activatedRoute.data = of({ adminImageSizes });
      comp.ngOnInit();

      expect(comp.editForm.value).toEqual(expect.objectContaining(adminImageSizes));
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<AdminImageSizes>>();
      const adminImageSizes = { id: 123 };
      jest.spyOn(adminImageSizesService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ adminImageSizes });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: adminImageSizes }));
      saveSubject.complete();

      // THEN
      expect(comp.previousState).toHaveBeenCalled();
      expect(adminImageSizesService.update).toHaveBeenCalledWith(adminImageSizes);
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<AdminImageSizes>>();
      const adminImageSizes = new AdminImageSizes();
      jest.spyOn(adminImageSizesService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ adminImageSizes });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: adminImageSizes }));
      saveSubject.complete();

      // THEN
      expect(adminImageSizesService.create).toHaveBeenCalledWith(adminImageSizes);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<AdminImageSizes>>();
      const adminImageSizes = { id: 123 };
      jest.spyOn(adminImageSizesService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ adminImageSizes });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(adminImageSizesService.update).toHaveBeenCalledWith(adminImageSizes);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
