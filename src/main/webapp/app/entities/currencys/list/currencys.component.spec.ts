import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { of } from 'rxjs';

import { CurrencysService } from '../service/currencys.service';

import { CurrencysComponent } from './currencys.component';

describe('Currencys Management Component', () => {
  let comp: CurrencysComponent;
  let fixture: ComponentFixture<CurrencysComponent>;
  let service: CurrencysService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      declarations: [CurrencysComponent],
    })
      .overrideTemplate(CurrencysComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(CurrencysComponent);
    comp = fixture.componentInstance;
    service = TestBed.inject(CurrencysService);

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
    expect(comp.currencys?.[0]).toEqual(expect.objectContaining({ id: 123 }));
  });
});
