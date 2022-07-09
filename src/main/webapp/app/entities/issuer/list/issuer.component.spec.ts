import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { of } from 'rxjs';

import { IssuerService } from '../service/issuer.service';

import { IssuerComponent } from './issuer.component';

describe('Issuer Management Component', () => {
  let comp: IssuerComponent;
  let fixture: ComponentFixture<IssuerComponent>;
  let service: IssuerService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      declarations: [IssuerComponent],
    })
      .overrideTemplate(IssuerComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(IssuerComponent);
    comp = fixture.componentInstance;
    service = TestBed.inject(IssuerService);

    const headers = new HttpHeaders();
    jest.spyOn(service, 'query').mockReturnValue(
      of(
        new HttpResponse({
          body: [{ id: 'ABC' }],
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
    expect(comp.issuers?.[0]).toEqual(expect.objectContaining({ id: 'ABC' }));
  });
});
