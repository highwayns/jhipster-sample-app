package io.github.jhipster.sample.service.mapper;

import io.github.jhipster.sample.domain.Currencies;
import io.github.jhipster.sample.domain.OrderLog;
import io.github.jhipster.sample.domain.OrderTypes;
import io.github.jhipster.sample.domain.SiteUsers;
import io.github.jhipster.sample.service.dto.CurrenciesDTO;
import io.github.jhipster.sample.service.dto.OrderLogDTO;
import io.github.jhipster.sample.service.dto.OrderTypesDTO;
import io.github.jhipster.sample.service.dto.SiteUsersDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link OrderLog} and its DTO {@link OrderLogDTO}.
 */
@Mapper(componentModel = "spring")
public interface OrderLogMapper extends EntityMapper<OrderLogDTO, OrderLog> {
    @Mapping(target = "siteUser", source = "siteUser", qualifiedByName = "siteUsersId")
    @Mapping(target = "currency", source = "currency", qualifiedByName = "currenciesId")
    @Mapping(target = "orderType", source = "orderType", qualifiedByName = "orderTypesId")
    OrderLogDTO toDto(OrderLog s);

    @Named("siteUsersId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    SiteUsersDTO toDtoSiteUsersId(SiteUsers siteUsers);

    @Named("currenciesId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    CurrenciesDTO toDtoCurrenciesId(Currencies currencies);

    @Named("orderTypesId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    OrderTypesDTO toDtoOrderTypesId(OrderTypes orderTypes);
}
