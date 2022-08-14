package io.github.jhipster.sample.service.mapper;

import io.github.jhipster.sample.domain.History;
import io.github.jhipster.sample.domain.HistoryActions;
import io.github.jhipster.sample.domain.Orders;
import io.github.jhipster.sample.domain.Requests;
import io.github.jhipster.sample.domain.SiteUsers;
import io.github.jhipster.sample.service.dto.HistoryActionsDTO;
import io.github.jhipster.sample.service.dto.HistoryDTO;
import io.github.jhipster.sample.service.dto.OrdersDTO;
import io.github.jhipster.sample.service.dto.RequestsDTO;
import io.github.jhipster.sample.service.dto.SiteUsersDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link History} and its DTO {@link HistoryDTO}.
 */
@Mapper(componentModel = "spring")
public interface HistoryMapper extends EntityMapper<HistoryDTO, History> {
    @Mapping(target = "historyAction", source = "historyAction", qualifiedByName = "historyActionsId")
    @Mapping(target = "orderId", source = "orderId", qualifiedByName = "ordersId")
    @Mapping(target = "requestId", source = "requestId", qualifiedByName = "requestsId")
    @Mapping(target = "siteUser", source = "siteUser", qualifiedByName = "siteUsersId")
    HistoryDTO toDto(History s);

    @Named("historyActionsId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    HistoryActionsDTO toDtoHistoryActionsId(HistoryActions historyActions);

    @Named("ordersId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    OrdersDTO toDtoOrdersId(Orders orders);

    @Named("requestsId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    RequestsDTO toDtoRequestsId(Requests requests);

    @Named("siteUsersId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    SiteUsersDTO toDtoSiteUsersId(SiteUsers siteUsers);
}
