import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { RequestsService } from '../service/requests.service';
import { IRequests, Requests } from '../requests.model';
import { ISiteUsers } from 'app/entities/site-users/site-users.model';
import { SiteUsersService } from 'app/entities/site-users/service/site-users.service';
import { ICurrencies } from 'app/entities/currencies/currencies.model';
import { CurrenciesService } from 'app/entities/currencies/service/currencies.service';
import { IRequestDescriptions } from 'app/entities/request-descriptions/request-descriptions.model';
import { RequestDescriptionsService } from 'app/entities/request-descriptions/service/request-descriptions.service';
import { IRequestStatus } from 'app/entities/request-status/request-status.model';
import { RequestStatusService } from 'app/entities/request-status/service/request-status.service';
import { IRequestTypes } from 'app/entities/request-types/request-types.model';
import { RequestTypesService } from 'app/entities/request-types/service/request-types.service';

import { RequestsUpdateComponent } from './requests-update.component';

describe('Requests Management Update Component', () => {
  let comp: RequestsUpdateComponent;
  let fixture: ComponentFixture<RequestsUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let requestsService: RequestsService;
  let siteUsersService: SiteUsersService;
  let currenciesService: CurrenciesService;
  let requestDescriptionsService: RequestDescriptionsService;
  let requestStatusService: RequestStatusService;
  let requestTypesService: RequestTypesService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [RequestsUpdateComponent],
      providers: [
        FormBuilder,
        {
          provide: ActivatedRoute,
          useValue: {
            params: from([{}]),
          },
        },
      ],
    })
      .overrideTemplate(RequestsUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(RequestsUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    requestsService = TestBed.inject(RequestsService);
    siteUsersService = TestBed.inject(SiteUsersService);
    currenciesService = TestBed.inject(CurrenciesService);
    requestDescriptionsService = TestBed.inject(RequestDescriptionsService);
    requestStatusService = TestBed.inject(RequestStatusService);
    requestTypesService = TestBed.inject(RequestTypesService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call siteUser query and add missing value', () => {
      const requests: IRequests = { id: 456 };
      const siteUser: ISiteUsers = { id: 15391 };
      requests.siteUser = siteUser;

      const siteUserCollection: ISiteUsers[] = [{ id: 92022 }];
      jest.spyOn(siteUsersService, 'query').mockReturnValue(of(new HttpResponse({ body: siteUserCollection })));
      const expectedCollection: ISiteUsers[] = [siteUser, ...siteUserCollection];
      jest.spyOn(siteUsersService, 'addSiteUsersToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ requests });
      comp.ngOnInit();

      expect(siteUsersService.query).toHaveBeenCalled();
      expect(siteUsersService.addSiteUsersToCollectionIfMissing).toHaveBeenCalledWith(siteUserCollection, siteUser);
      expect(comp.siteUsersCollection).toEqual(expectedCollection);
    });

    it('Should call currency query and add missing value', () => {
      const requests: IRequests = { id: 456 };
      const currency: ICurrencies = { id: 48758 };
      requests.currency = currency;

      const currencyCollection: ICurrencies[] = [{ id: 78063 }];
      jest.spyOn(currenciesService, 'query').mockReturnValue(of(new HttpResponse({ body: currencyCollection })));
      const expectedCollection: ICurrencies[] = [currency, ...currencyCollection];
      jest.spyOn(currenciesService, 'addCurrenciesToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ requests });
      comp.ngOnInit();

      expect(currenciesService.query).toHaveBeenCalled();
      expect(currenciesService.addCurrenciesToCollectionIfMissing).toHaveBeenCalledWith(currencyCollection, currency);
      expect(comp.currenciesCollection).toEqual(expectedCollection);
    });

    it('Should call description query and add missing value', () => {
      const requests: IRequests = { id: 456 };
      const description: IRequestDescriptions = { id: 41016 };
      requests.description = description;

      const descriptionCollection: IRequestDescriptions[] = [{ id: 16876 }];
      jest.spyOn(requestDescriptionsService, 'query').mockReturnValue(of(new HttpResponse({ body: descriptionCollection })));
      const expectedCollection: IRequestDescriptions[] = [description, ...descriptionCollection];
      jest.spyOn(requestDescriptionsService, 'addRequestDescriptionsToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ requests });
      comp.ngOnInit();

      expect(requestDescriptionsService.query).toHaveBeenCalled();
      expect(requestDescriptionsService.addRequestDescriptionsToCollectionIfMissing).toHaveBeenCalledWith(
        descriptionCollection,
        description
      );
      expect(comp.descriptionsCollection).toEqual(expectedCollection);
    });

    it('Should call requestStatus query and add missing value', () => {
      const requests: IRequests = { id: 456 };
      const requestStatus: IRequestStatus = { id: 83183 };
      requests.requestStatus = requestStatus;

      const requestStatusCollection: IRequestStatus[] = [{ id: 92645 }];
      jest.spyOn(requestStatusService, 'query').mockReturnValue(of(new HttpResponse({ body: requestStatusCollection })));
      const expectedCollection: IRequestStatus[] = [requestStatus, ...requestStatusCollection];
      jest.spyOn(requestStatusService, 'addRequestStatusToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ requests });
      comp.ngOnInit();

      expect(requestStatusService.query).toHaveBeenCalled();
      expect(requestStatusService.addRequestStatusToCollectionIfMissing).toHaveBeenCalledWith(requestStatusCollection, requestStatus);
      expect(comp.requestStatusesCollection).toEqual(expectedCollection);
    });

    it('Should call requestType query and add missing value', () => {
      const requests: IRequests = { id: 456 };
      const requestType: IRequestTypes = { id: 17914 };
      requests.requestType = requestType;

      const requestTypeCollection: IRequestTypes[] = [{ id: 65924 }];
      jest.spyOn(requestTypesService, 'query').mockReturnValue(of(new HttpResponse({ body: requestTypeCollection })));
      const expectedCollection: IRequestTypes[] = [requestType, ...requestTypeCollection];
      jest.spyOn(requestTypesService, 'addRequestTypesToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ requests });
      comp.ngOnInit();

      expect(requestTypesService.query).toHaveBeenCalled();
      expect(requestTypesService.addRequestTypesToCollectionIfMissing).toHaveBeenCalledWith(requestTypeCollection, requestType);
      expect(comp.requestTypesCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const requests: IRequests = { id: 456 };
      const siteUser: ISiteUsers = { id: 39995 };
      requests.siteUser = siteUser;
      const currency: ICurrencies = { id: 4918 };
      requests.currency = currency;
      const description: IRequestDescriptions = { id: 86830 };
      requests.description = description;
      const requestStatus: IRequestStatus = { id: 62220 };
      requests.requestStatus = requestStatus;
      const requestType: IRequestTypes = { id: 12071 };
      requests.requestType = requestType;

      activatedRoute.data = of({ requests });
      comp.ngOnInit();

      expect(comp.editForm.value).toEqual(expect.objectContaining(requests));
      expect(comp.siteUsersCollection).toContain(siteUser);
      expect(comp.currenciesCollection).toContain(currency);
      expect(comp.descriptionsCollection).toContain(description);
      expect(comp.requestStatusesCollection).toContain(requestStatus);
      expect(comp.requestTypesCollection).toContain(requestType);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Requests>>();
      const requests = { id: 123 };
      jest.spyOn(requestsService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ requests });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: requests }));
      saveSubject.complete();

      // THEN
      expect(comp.previousState).toHaveBeenCalled();
      expect(requestsService.update).toHaveBeenCalledWith(requests);
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Requests>>();
      const requests = new Requests();
      jest.spyOn(requestsService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ requests });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: requests }));
      saveSubject.complete();

      // THEN
      expect(requestsService.create).toHaveBeenCalledWith(requests);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Requests>>();
      const requests = { id: 123 };
      jest.spyOn(requestsService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ requests });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(requestsService.update).toHaveBeenCalledWith(requests);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Tracking relationships identifiers', () => {
    describe('trackSiteUsersById', () => {
      it('Should return tracked SiteUsers primary key', () => {
        const entity = { id: 123 };
        const trackResult = comp.trackSiteUsersById(0, entity);
        expect(trackResult).toEqual(entity.id);
      });
    });

    describe('trackCurrenciesById', () => {
      it('Should return tracked Currencies primary key', () => {
        const entity = { id: 123 };
        const trackResult = comp.trackCurrenciesById(0, entity);
        expect(trackResult).toEqual(entity.id);
      });
    });

    describe('trackRequestDescriptionsById', () => {
      it('Should return tracked RequestDescriptions primary key', () => {
        const entity = { id: 123 };
        const trackResult = comp.trackRequestDescriptionsById(0, entity);
        expect(trackResult).toEqual(entity.id);
      });
    });

    describe('trackRequestStatusById', () => {
      it('Should return tracked RequestStatus primary key', () => {
        const entity = { id: 123 };
        const trackResult = comp.trackRequestStatusById(0, entity);
        expect(trackResult).toEqual(entity.id);
      });
    });

    describe('trackRequestTypesById', () => {
      it('Should return tracked RequestTypes primary key', () => {
        const entity = { id: 123 };
        const trackResult = comp.trackRequestTypesById(0, entity);
        expect(trackResult).toEqual(entity.id);
      });
    });
  });
});
