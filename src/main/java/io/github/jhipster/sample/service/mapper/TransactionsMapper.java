package io.github.jhipster.sample.service.mapper;

import io.github.jhipster.sample.domain.Currencies;
import io.github.jhipster.sample.domain.SiteUsers;
import io.github.jhipster.sample.domain.TransactionTypes;
import io.github.jhipster.sample.domain.Transactions;
import io.github.jhipster.sample.service.dto.CurrenciesDTO;
import io.github.jhipster.sample.service.dto.SiteUsersDTO;
import io.github.jhipster.sample.service.dto.TransactionTypesDTO;
import io.github.jhipster.sample.service.dto.TransactionsDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Transactions} and its DTO {@link TransactionsDTO}.
 */
@Mapper(componentModel = "spring")
public interface TransactionsMapper extends EntityMapper<TransactionsDTO, Transactions> {
    @Mapping(target = "siteUser", source = "siteUser", qualifiedByName = "siteUsersId")
    @Mapping(target = "siteUser1", source = "siteUser1", qualifiedByName = "siteUsersId")
    @Mapping(target = "transactionType", source = "transactionType", qualifiedByName = "transactionTypesId")
    @Mapping(target = "transactionType1", source = "transactionType1", qualifiedByName = "transactionTypesId")
    @Mapping(target = "currency1", source = "currency1", qualifiedByName = "currenciesId")
    @Mapping(target = "convertFromCurrency", source = "convertFromCurrency", qualifiedByName = "currenciesId")
    @Mapping(target = "convertToCurrency", source = "convertToCurrency", qualifiedByName = "currenciesId")
    TransactionsDTO toDto(Transactions s);

    @Named("siteUsersId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    SiteUsersDTO toDtoSiteUsersId(SiteUsers siteUsers);

    @Named("transactionTypesId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    TransactionTypesDTO toDtoTransactionTypesId(TransactionTypes transactionTypes);

    @Named("currenciesId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    CurrenciesDTO toDtoCurrenciesId(Currencies currencies);
}
