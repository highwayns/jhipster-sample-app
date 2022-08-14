import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { EmailsService } from '../service/emails.service';
import { IEmails, Emails } from '../emails.model';

import { EmailsUpdateComponent } from './emails-update.component';

describe('Emails Management Update Component', () => {
  let comp: EmailsUpdateComponent;
  let fixture: ComponentFixture<EmailsUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let emailsService: EmailsService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [EmailsUpdateComponent],
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
      .overrideTemplate(EmailsUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(EmailsUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    emailsService = TestBed.inject(EmailsService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const emails: IEmails = { id: 456 };

      activatedRoute.data = of({ emails });
      comp.ngOnInit();

      expect(comp.editForm.value).toEqual(expect.objectContaining(emails));
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Emails>>();
      const emails = { id: 123 };
      jest.spyOn(emailsService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ emails });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: emails }));
      saveSubject.complete();

      // THEN
      expect(comp.previousState).toHaveBeenCalled();
      expect(emailsService.update).toHaveBeenCalledWith(emails);
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Emails>>();
      const emails = new Emails();
      jest.spyOn(emailsService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ emails });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: emails }));
      saveSubject.complete();

      // THEN
      expect(emailsService.create).toHaveBeenCalledWith(emails);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Emails>>();
      const emails = { id: 123 };
      jest.spyOn(emailsService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ emails });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(emailsService.update).toHaveBeenCalledWith(emails);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
