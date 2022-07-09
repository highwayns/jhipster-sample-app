import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { ResultAttributesDetailComponent } from './result-attributes-detail.component';

describe('ResultAttributes Management Detail Component', () => {
  let comp: ResultAttributesDetailComponent;
  let fixture: ComponentFixture<ResultAttributesDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [ResultAttributesDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ resultAttributes: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(ResultAttributesDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(ResultAttributesDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load resultAttributes on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.resultAttributes).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
