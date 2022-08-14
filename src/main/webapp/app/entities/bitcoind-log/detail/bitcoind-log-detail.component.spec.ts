import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { BitcoindLogDetailComponent } from './bitcoind-log-detail.component';

describe('BitcoindLog Management Detail Component', () => {
  let comp: BitcoindLogDetailComponent;
  let fixture: ComponentFixture<BitcoindLogDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [BitcoindLogDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ bitcoindLog: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(BitcoindLogDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(BitcoindLogDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load bitcoindLog on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.bitcoindLog).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
