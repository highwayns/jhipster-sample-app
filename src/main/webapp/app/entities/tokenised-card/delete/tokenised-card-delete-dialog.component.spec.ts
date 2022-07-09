jest.mock('@ng-bootstrap/ng-bootstrap');

import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { of } from 'rxjs';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { TokenisedCardService } from '../service/tokenised-card.service';

import { TokenisedCardDeleteDialogComponent } from './tokenised-card-delete-dialog.component';

describe('TokenisedCard Management Delete Component', () => {
  let comp: TokenisedCardDeleteDialogComponent;
  let fixture: ComponentFixture<TokenisedCardDeleteDialogComponent>;
  let service: TokenisedCardService;
  let mockActiveModal: NgbActiveModal;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      declarations: [TokenisedCardDeleteDialogComponent],
      providers: [NgbActiveModal],
    })
      .overrideTemplate(TokenisedCardDeleteDialogComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(TokenisedCardDeleteDialogComponent);
    comp = fixture.componentInstance;
    service = TestBed.inject(TokenisedCardService);
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
