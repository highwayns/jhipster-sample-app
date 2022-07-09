import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { IssuerDetailComponent } from './issuer-detail.component';

describe('Issuer Management Detail Component', () => {
  let comp: IssuerDetailComponent;
  let fixture: ComponentFixture<IssuerDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [IssuerDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ issuer: { id: 'ABC' } }) },
        },
      ],
    })
      .overrideTemplate(IssuerDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(IssuerDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load issuer on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.issuer).toEqual(expect.objectContaining({ id: 'ABC' }));
    });
  });
});
