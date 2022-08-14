import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { ContentFilesDetailComponent } from './content-files-detail.component';

describe('ContentFiles Management Detail Component', () => {
  let comp: ContentFilesDetailComponent;
  let fixture: ComponentFixture<ContentFilesDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [ContentFilesDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ contentFiles: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(ContentFilesDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(ContentFilesDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load contentFiles on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.contentFiles).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
