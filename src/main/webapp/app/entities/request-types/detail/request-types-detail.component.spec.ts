import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { RequestTypesDetailComponent } from './request-types-detail.component';

describe('RequestTypes Management Detail Component', () => {
  let comp: RequestTypesDetailComponent;
  let fixture: ComponentFixture<RequestTypesDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [RequestTypesDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ requestTypes: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(RequestTypesDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(RequestTypesDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load requestTypes on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.requestTypes).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
