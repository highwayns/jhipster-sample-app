import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { of } from 'rxjs';

import { CardTokenDataService } from '../service/card-token-data.service';

import { CardTokenDataComponent } from './card-token-data.component';

describe('CardTokenData Management Component', () => {
  let comp: CardTokenDataComponent;
  let fixture: ComponentFixture<CardTokenDataComponent>;
  let service: CardTokenDataService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      declarations: [CardTokenDataComponent],
    })
      .overrideTemplate(CardTokenDataComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(CardTokenDataComponent);
    comp = fixture.componentInstance;
    service = TestBed.inject(CardTokenDataService);

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
    expect(comp.cardTokenData?.[0]).toEqual(expect.objectContaining({ id: 123 }));
  });
});
