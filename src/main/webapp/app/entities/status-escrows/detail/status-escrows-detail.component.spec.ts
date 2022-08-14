import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { StatusEscrowsDetailComponent } from './status-escrows-detail.component';

describe('StatusEscrows Management Detail Component', () => {
  let comp: StatusEscrowsDetailComponent;
  let fixture: ComponentFixture<StatusEscrowsDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [StatusEscrowsDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ statusEscrows: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(StatusEscrowsDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(StatusEscrowsDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load statusEscrows on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.statusEscrows).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
