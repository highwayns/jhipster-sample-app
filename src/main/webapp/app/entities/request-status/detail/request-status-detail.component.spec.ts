import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { RequestStatusDetailComponent } from './request-status-detail.component';

describe('RequestStatus Management Detail Component', () => {
  let comp: RequestStatusDetailComponent;
  let fixture: ComponentFixture<RequestStatusDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [RequestStatusDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ requestStatus: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(RequestStatusDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(RequestStatusDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load requestStatus on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.requestStatus).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
