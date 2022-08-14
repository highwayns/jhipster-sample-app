import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { RequestDescriptionsDetailComponent } from './request-descriptions-detail.component';

describe('RequestDescriptions Management Detail Component', () => {
  let comp: RequestDescriptionsDetailComponent;
  let fixture: ComponentFixture<RequestDescriptionsDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [RequestDescriptionsDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ requestDescriptions: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(RequestDescriptionsDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(RequestDescriptionsDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load requestDescriptions on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.requestDescriptions).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
