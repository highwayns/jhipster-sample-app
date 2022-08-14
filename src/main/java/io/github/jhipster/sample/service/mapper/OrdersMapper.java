package io.github.jhipster.sample.service.mapper;

import io.github.jhipster.sample.domain.Currencies;
import io.github.jhipster.sample.domain.OrderLog;
import io.github.jhipster.sample.domain.OrderTypes;
import io.github.jhipster.sample.domain.Orders;
import io.github.jhipster.sample.domain.SiteUsers;
import io.github.jhipster.sample.service.dto.CurrenciesDTO;
import io.github.jhipster.sample.service.dto.OrderLogDTO;
import io.github.jhipster.sample.service.dto.OrderTypesDTO;
import io.github.jhipster.sample.service.dto.OrdersDTO;
import io.github.jhipster.sample.service.dto.SiteUsersDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Orders} and its DTO {@link OrdersDTO}.
 */
@Mapper(componentModel = "spring")
public interface OrdersMapper extends EntityMapper<OrdersDTO, Orders> {
    @Mapping(target = "orderType", source = "orderType", qualifiedByName = "orderTypesId")
    @Mapping(target = "siteUser", source = "siteUser", qualifiedByName = "siteUsersId")
    @Mapping(target = "currency", source = "currency", qualifiedByName = "currenciesId")
    @Mapping(target = "logId", source = "logId", qualifiedByName = "orderLogId")
    OrdersDTO toDto(Orders s);

    @Named("orderTypesId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    OrderTypesDTO toDtoOrderTypesId(OrderTypes orderTypes);

    @Named("siteUsersId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    SiteUsersDTO toDtoSiteUsersId(SiteUsers siteUsers);

    @Named("currenciesId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    CurrenciesDTO toDtoCurrenciesId(Currencies currencies);

    @Named("orderLogId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    OrderLogDTO toDtoOrderLogId(OrderLog orderLog);
}
