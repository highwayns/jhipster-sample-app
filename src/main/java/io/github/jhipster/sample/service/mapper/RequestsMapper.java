package io.github.jhipster.sample.service.mapper;

import io.github.jhipster.sample.domain.Currencies;
import io.github.jhipster.sample.domain.RequestDescriptions;
import io.github.jhipster.sample.domain.RequestStatus;
import io.github.jhipster.sample.domain.RequestTypes;
import io.github.jhipster.sample.domain.Requests;
import io.github.jhipster.sample.domain.SiteUsers;
import io.github.jhipster.sample.service.dto.CurrenciesDTO;
import io.github.jhipster.sample.service.dto.RequestDescriptionsDTO;
import io.github.jhipster.sample.service.dto.RequestStatusDTO;
import io.github.jhipster.sample.service.dto.RequestTypesDTO;
import io.github.jhipster.sample.service.dto.RequestsDTO;
import io.github.jhipster.sample.service.dto.SiteUsersDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Requests} and its DTO {@link RequestsDTO}.
 */
@Mapper(componentModel = "spring")
public interface RequestsMapper extends EntityMapper<RequestsDTO, Requests> {
    @Mapping(target = "siteUser", source = "siteUser", qualifiedByName = "siteUsersId")
    @Mapping(target = "currency", source = "currency", qualifiedByName = "currenciesId")
    @Mapping(target = "description", source = "description", qualifiedByName = "requestDescriptionsId")
    @Mapping(target = "requestStatus", source = "requestStatus", qualifiedByName = "requestStatusId")
    @Mapping(target = "requestType", source = "requestType", qualifiedByName = "requestTypesId")
    RequestsDTO toDto(Requests s);

    @Named("siteUsersId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    SiteUsersDTO toDtoSiteUsersId(SiteUsers siteUsers);

    @Named("currenciesId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    CurrenciesDTO toDtoCurrenciesId(Currencies currencies);

    @Named("requestDescriptionsId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    RequestDescriptionsDTO toDtoRequestDescriptionsId(RequestDescriptions requestDescriptions);

    @Named("requestStatusId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    RequestStatusDTO toDtoRequestStatusId(RequestStatus requestStatus);

    @Named("requestTypesId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    RequestTypesDTO toDtoRequestTypesId(RequestTypes requestTypes);
}
