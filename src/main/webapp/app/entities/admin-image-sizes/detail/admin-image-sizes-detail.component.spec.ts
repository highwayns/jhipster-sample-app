import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { AdminImageSizesDetailComponent } from './admin-image-sizes-detail.component';

describe('AdminImageSizes Management Detail Component', () => {
  let comp: AdminImageSizesDetailComponent;
  let fixture: ComponentFixture<AdminImageSizesDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [AdminImageSizesDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ adminImageSizes: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(AdminImageSizesDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(AdminImageSizesDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load adminImageSizes on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.adminImageSizes).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
