import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { SettingsFilesDetailComponent } from './settings-files-detail.component';

describe('SettingsFiles Management Detail Component', () => {
  let comp: SettingsFilesDetailComponent;
  let fixture: ComponentFixture<SettingsFilesDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [SettingsFilesDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ settingsFiles: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(SettingsFilesDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(SettingsFilesDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load settingsFiles on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.settingsFiles).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
