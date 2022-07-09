import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { of } from 'rxjs';

import { RefundService } from '../service/refund.service';

import { RefundComponent } from './refund.component';

describe('Refund Management Component', () => {
  let comp: RefundComponent;
  let fixture: ComponentFixture<RefundComponent>;
  let service: RefundService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      declarations: [RefundComponent],
    })
      .overrideTemplate(RefundComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(RefundComponent);
    comp = fixture.componentInstance;
    service = TestBed.inject(RefundService);

    const headers = new HttpHeaders();
    jest.spyOn(service, 'query').mockReturnValue(
      of(
        new HttpResponse({
          body: [{ id: 123 }],
          headers,
        })
      )
    );
  });

  it('Should call load all on init', () => {
    // WHEN
    comp.ngOnInit();

    // THEN
    expect(service.query).toHaveBeenCalled();
    expect(comp.refunds?.[0]).toEqual(expect.objectContaining({ id: 123 }));
  });
});
