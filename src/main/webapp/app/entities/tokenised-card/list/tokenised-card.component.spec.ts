import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { of } from 'rxjs';

import { TokenisedCardService } from '../service/tokenised-card.service';

import { TokenisedCardComponent } from './tokenised-card.component';

describe('TokenisedCard Management Component', () => {
  let comp: TokenisedCardComponent;
  let fixture: ComponentFixture<TokenisedCardComponent>;
  let service: TokenisedCardService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      declarations: [TokenisedCardComponent],
    })
      .overrideTemplate(TokenisedCardComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(TokenisedCardComponent);
    comp = fixture.componentInstance;
    service = TestBed.inject(TokenisedCardService);

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
    expect(comp.tokenisedCards?.[0]).toEqual(expect.objectContaining({ id: 123 }));
  });
});
