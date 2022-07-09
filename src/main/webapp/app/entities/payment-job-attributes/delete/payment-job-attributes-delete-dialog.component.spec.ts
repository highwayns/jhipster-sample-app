jest.mock('@ng-bootstrap/ng-bootstrap');

import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { of } from 'rxjs';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { PaymentJobAttributesService } from '../service/payment-job-attributes.service';

import { PaymentJobAttributesDeleteDialogComponent } from './payment-job-attributes-delete-dialog.component';

describe('PaymentJobAttributes Management Delete Component', () => {
  let comp: PaymentJobAttributesDeleteDialogComponent;
  let fixture: ComponentFixture<PaymentJobAttributesDeleteDialogComponent>;
  let service: PaymentJobAttributesService;
  let mockActiveModal: NgbActiveModal;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      declarations: [PaymentJobAttributesDeleteDialogComponent],
      providers: [NgbActiveModal],
    })
      .overrideTemplate(PaymentJobAttributesDeleteDialogComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(PaymentJobAttributesDeleteDialogComponent);
    comp = fixture.componentInstance;
    service = TestBed.inject(PaymentJobAttributesService);
    mockActiveModal = TestBed.inject(NgbActiveModal);
  });

  describe('confirmDelete', () => {
    it('Should call delete service on confirmDelete', inject(
      [],
      fakeAsync(() => {
        // GIVEN
        jest.spyOn(service, 'delete').mockReturnValue(of(new HttpResponse({ body: {} })));

        // WHEN
        comp.confirmDelete(123);
        tick();

        // THEN
        expect(service.delete).toHaveBeenCalledWith(123);
        expect(mockActiveModal.close).toHaveBeenCalledWith('deleted');
      })
    ));

    it('Should not call delete service on clear', () => {
      // GIVEN
      jest.spyOn(service, 'delete');

      // WHEN
      comp.cancel();

      // THEN
      expect(service.delete).not.toHaveBeenCalled();
      expect(mockActiveModal.close).not.toHaveBeenCalled();
      expect(mockActiveModal.dismiss).toHaveBeenCalled();
    });
  });
});
