import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { IpAccessLogService } from '../service/ip-access-log.service';
import { IIpAccessLog, IpAccessLog } from '../ip-access-log.model';

import { IpAccessLogUpdateComponent } from './ip-access-log-update.component';

describe('IpAccessLog Management Update Component', () => {
  let comp: IpAccessLogUpdateComponent;
  let fixture: ComponentFixture<IpAccessLogUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let ipAccessLogService: IpAccessLogService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [IpAccessLogUpdateComponent],
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
      .overrideTemplate(IpAccessLogUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(IpAccessLogUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    ipAccessLogService = TestBed.inject(IpAccessLogService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const ipAccessLog: IIpAccessLog = { id: 456 };

      activatedRoute.data = of({ ipAccessLog });
      comp.ngOnInit();

      expect(comp.editForm.value).toEqual(expect.objectContaining(ipAccessLog));
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IpAccessLog>>();
      const ipAccessLog = { id: 123 };
      jest.spyOn(ipAccessLogService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ ipAccessLog });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: ipAccessLog }));
      saveSubject.complete();

      // THEN
      expect(comp.previousState).toHaveBeenCalled();
      expect(ipAccessLogService.update).toHaveBeenCalledWith(ipAccessLog);
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IpAccessLog>>();
      const ipAccessLog = new IpAccessLog();
      jest.spyOn(ipAccessLogService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ ipAccessLog });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: ipAccessLog }));
      saveSubject.complete();

      // THEN
      expect(ipAccessLogService.create).toHaveBeenCalledWith(ipAccessLog);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IpAccessLog>>();
      const ipAccessLog = { id: 123 };
      jest.spyOn(ipAccessLogService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ ipAccessLog });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(ipAccessLogService.update).toHaveBeenCalledWith(ipAccessLog);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
