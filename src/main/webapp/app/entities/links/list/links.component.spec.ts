import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { of } from 'rxjs';

import { LinksService } from '../service/links.service';

import { LinksComponent } from './links.component';

describe('Links Management Component', () => {
  let comp: LinksComponent;
  let fixture: ComponentFixture<LinksComponent>;
  let service: LinksService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      declarations: [LinksComponent],
    })
      .overrideTemplate(LinksComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(LinksComponent);
    comp = fixture.componentInstance;
    service = TestBed.inject(LinksService);

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
    expect(comp.links?.[0]).toEqual(expect.objectContaining({ id: 123 }));
  });
});
