package io.github.jhipster.sample.service.mapper;

import io.github.jhipster.sample.domain.BankAccounts;
import io.github.jhipster.sample.domain.Currencies;
import io.github.jhipster.sample.domain.SiteUsers;
import io.github.jhipster.sample.service.dto.BankAccountsDTO;
import io.github.jhipster.sample.service.dto.CurrenciesDTO;
import io.github.jhipster.sample.service.dto.SiteUsersDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link BankAccounts} and its DTO {@link BankAccountsDTO}.
 */
@Mapper(componentModel = "spring")
public interface BankAccountsMapper extends EntityMapper<BankAccountsDTO, BankAccounts> {
    @Mapping(target = "siteUser", source = "siteUser", qualifiedByName = "siteUsersId")
    @Mapping(target = "currency", source = "currency", qualifiedByName = "currenciesId")
    BankAccountsDTO toDto(BankAccounts s);

    @Named("siteUsersId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    SiteUsersDTO toDtoSiteUsersId(SiteUsers siteUsers);

    @Named("currenciesId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    CurrenciesDTO toDtoCurrenciesId(Currencies currencies);
}
