import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { IsoCountriesDetailComponent } from './iso-countries-detail.component';

describe('IsoCountries Management Detail Component', () => {
  let comp: IsoCountriesDetailComponent;
  let fixture: ComponentFixture<IsoCountriesDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [IsoCountriesDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ isoCountries: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(IsoCountriesDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(IsoCountriesDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load isoCountries on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.isoCountries).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
