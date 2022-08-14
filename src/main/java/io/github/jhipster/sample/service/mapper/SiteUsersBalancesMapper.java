package io.github.jhipster.sample.service.mapper;

import io.github.jhipster.sample.domain.Currencies;
import io.github.jhipster.sample.domain.SiteUsers;
import io.github.jhipster.sample.domain.SiteUsersBalances;
import io.github.jhipster.sample.service.dto.CurrenciesDTO;
import io.github.jhipster.sample.service.dto.SiteUsersBalancesDTO;
import io.github.jhipster.sample.service.dto.SiteUsersDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link SiteUsersBalances} and its DTO {@link SiteUsersBalancesDTO}.
 */
@Mapper(componentModel = "spring")
public interface SiteUsersBalancesMapper extends EntityMapper<SiteUsersBalancesDTO, SiteUsersBalances> {
    @Mapping(target = "siteUser", source = "siteUser", qualifiedByName = "siteUsersId")
    @Mapping(target = "currency", source = "currency", qualifiedByName = "currenciesId")
    SiteUsersBalancesDTO toDto(SiteUsersBalances s);

    @Named("siteUsersId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    SiteUsersDTO toDtoSiteUsersId(SiteUsers siteUsers);

    @Named("currenciesId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    CurrenciesDTO toDtoCurrenciesId(Currencies currencies);
}
