import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { LangDetailComponent } from './lang-detail.component';

describe('Lang Management Detail Component', () => {
  let comp: LangDetailComponent;
  let fixture: ComponentFixture<LangDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [LangDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ lang: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(LangDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(LangDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load lang on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.lang).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
