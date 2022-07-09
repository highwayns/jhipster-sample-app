import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { of } from 'rxjs';

import { AbuseTriggerService } from '../service/abuse-trigger.service';

import { AbuseTriggerComponent } from './abuse-trigger.component';

describe('AbuseTrigger Management Component', () => {
  let comp: AbuseTriggerComponent;
  let fixture: ComponentFixture<AbuseTriggerComponent>;
  let service: AbuseTriggerService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      declarations: [AbuseTriggerComponent],
    })
      .overrideTemplate(AbuseTriggerComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(AbuseTriggerComponent);
    comp = fixture.componentInstance;
    service = TestBed.inject(AbuseTriggerService);

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
    expect(comp.abuseTriggers?.[0]).toEqual(expect.objectContaining({ id: 123 }));
  });
});
