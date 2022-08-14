import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { ConversionsDetailComponent } from './conversions-detail.component';

describe('Conversions Management Detail Component', () => {
  let comp: ConversionsDetailComponent;
  let fixture: ComponentFixture<ConversionsDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [ConversionsDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ conversions: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(ConversionsDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(ConversionsDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load conversions on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.conversions).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
