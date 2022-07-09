import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { of } from 'rxjs';

import { RecurrenceCriteriaService } from '../service/recurrence-criteria.service';

import { RecurrenceCriteriaComponent } from './recurrence-criteria.component';

describe('RecurrenceCriteria Management Component', () => {
  let comp: RecurrenceCriteriaComponent;
  let fixture: ComponentFixture<RecurrenceCriteriaComponent>;
  let service: RecurrenceCriteriaService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      declarations: [RecurrenceCriteriaComponent],
    })
      .overrideTemplate(RecurrenceCriteriaComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(RecurrenceCriteriaComponent);
    comp = fixture.componentInstance;
    service = TestBed.inject(RecurrenceCriteriaService);

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
    expect(comp.recurrenceCriteria?.[0]).toEqual(expect.objectContaining({ id: 123 }));
  });
});
