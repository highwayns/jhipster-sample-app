import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { ApiKeysDetailComponent } from './api-keys-detail.component';

describe('ApiKeys Management Detail Component', () => {
  let comp: ApiKeysDetailComponent;
  let fixture: ComponentFixture<ApiKeysDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [ApiKeysDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ apiKeys: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(ApiKeysDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(ApiKeysDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load apiKeys on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.apiKeys).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
