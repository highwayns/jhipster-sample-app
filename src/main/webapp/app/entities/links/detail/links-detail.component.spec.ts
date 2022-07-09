import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { LinksDetailComponent } from './links-detail.component';

describe('Links Management Detail Component', () => {
  let comp: LinksDetailComponent;
  let fixture: ComponentFixture<LinksDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [LinksDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ links: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(LinksDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(LinksDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load links on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.links).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
