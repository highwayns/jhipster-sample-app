import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { CaptureService } from '../service/capture.service';
import { ICapture, Capture } from '../capture.model';

import { CaptureUpdateComponent } from './capture-update.component';

describe('Capture Management Update Component', () => {
  let comp: CaptureUpdateComponent;
  let fixture: ComponentFixture<CaptureUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let captureService: CaptureService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [CaptureUpdateComponent],
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
      .overrideTemplate(CaptureUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(CaptureUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    captureService = TestBed.inject(CaptureService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const capture: ICapture = { id: 456 };

      activatedRoute.data = of({ capture });
      comp.ngOnInit();

      expect(comp.editForm.value).toEqual(expect.objectContaining(capture));
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Capture>>();
      const capture = { id: 123 };
      jest.spyOn(captureService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ capture });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: capture }));
      saveSubject.complete();

      // THEN
      expect(comp.previousState).toHaveBeenCalled();
      expect(captureService.update).toHaveBeenCalledWith(capture);
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Capture>>();
      const capture = new Capture();
      jest.spyOn(captureService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ capture });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: capture }));
      saveSubject.complete();

      // THEN
      expect(captureService.create).toHaveBeenCalledWith(capture);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Capture>>();
      const capture = { id: 123 };
      jest.spyOn(captureService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ capture });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(captureService.update).toHaveBeenCalledWith(capture);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
