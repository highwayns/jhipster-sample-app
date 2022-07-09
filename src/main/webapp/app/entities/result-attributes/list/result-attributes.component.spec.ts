import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { of } from 'rxjs';

import { ResultAttributesService } from '../service/result-attributes.service';

import { ResultAttributesComponent } from './result-attributes.component';

describe('ResultAttributes Management Component', () => {
  let comp: ResultAttributesComponent;
  let fixture: ComponentFixture<ResultAttributesComponent>;
  let service: ResultAttributesService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      declarations: [ResultAttributesComponent],
    })
      .overrideTemplate(ResultAttributesComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(ResultAttributesComponent);
    comp = fixture.componentInstance;
    service = TestBed.inject(ResultAttributesService);

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
    expect(comp.resultAttributes?.[0]).toEqual(expect.objectContaining({ id: 123 }));
  });
});
