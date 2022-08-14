import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { InnodbLockMonitorService } from '../service/innodb-lock-monitor.service';
import { IInnodbLockMonitor, InnodbLockMonitor } from '../innodb-lock-monitor.model';

import { InnodbLockMonitorUpdateComponent } from './innodb-lock-monitor-update.component';

describe('InnodbLockMonitor Management Update Component', () => {
  let comp: InnodbLockMonitorUpdateComponent;
  let fixture: ComponentFixture<InnodbLockMonitorUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let innodbLockMonitorService: InnodbLockMonitorService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [InnodbLockMonitorUpdateComponent],
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
      .overrideTemplate(InnodbLockMonitorUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(InnodbLockMonitorUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    innodbLockMonitorService = TestBed.inject(InnodbLockMonitorService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const innodbLockMonitor: IInnodbLockMonitor = { id: 456 };

      activatedRoute.data = of({ innodbLockMonitor });
      comp.ngOnInit();

      expect(comp.editForm.value).toEqual(expect.objectContaining(innodbLockMonitor));
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<InnodbLockMonitor>>();
      const innodbLockMonitor = { id: 123 };
      jest.spyOn(innodbLockMonitorService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ innodbLockMonitor });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: innodbLockMonitor }));
      saveSubject.complete();

      // THEN
      expect(comp.previousState).toHaveBeenCalled();
      expect(innodbLockMonitorService.update).toHaveBeenCalledWith(innodbLockMonitor);
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<InnodbLockMonitor>>();
      const innodbLockMonitor = new InnodbLockMonitor();
      jest.spyOn(innodbLockMonitorService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ innodbLockMonitor });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: innodbLockMonitor }));
      saveSubject.complete();

      // THEN
      expect(innodbLockMonitorService.create).toHaveBeenCalledWith(innodbLockMonitor);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<InnodbLockMonitor>>();
      const innodbLockMonitor = { id: 123 };
      jest.spyOn(innodbLockMonitorService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ innodbLockMonitor });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(innodbLockMonitorService.update).toHaveBeenCalledWith(innodbLockMonitor);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
