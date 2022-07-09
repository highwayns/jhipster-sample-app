import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { of } from 'rxjs';

import { ParametersService } from '../service/parameters.service';

import { ParametersComponent } from './parameters.component';

describe('Parameters Management Component', () => {
  let comp: ParametersComponent;
  let fixture: ComponentFixture<ParametersComponent>;
  let service: ParametersService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      declarations: [ParametersComponent],
    })
      .overrideTemplate(ParametersComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(ParametersComponent);
    comp = fixture.componentInstance;
    service = TestBed.inject(ParametersService);

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
    expect(comp.parameters?.[0]).toEqual(expect.objectContaining({ id: 123 }));
  });
});
