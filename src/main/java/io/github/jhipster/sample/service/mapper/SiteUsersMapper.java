package io.github.jhipster.sample.service.mapper;

import io.github.jhipster.sample.domain.Currencies;
import io.github.jhipster.sample.domain.FeeSchedule;
import io.github.jhipster.sample.domain.IsoCountries;
import io.github.jhipster.sample.domain.SiteUsers;
import io.github.jhipster.sample.service.dto.CurrenciesDTO;
import io.github.jhipster.sample.service.dto.FeeScheduleDTO;
import io.github.jhipster.sample.service.dto.IsoCountriesDTO;
import io.github.jhipster.sample.service.dto.SiteUsersDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link SiteUsers} and its DTO {@link SiteUsersDTO}.
 */
@Mapper(componentModel = "spring")
public interface SiteUsersMapper extends EntityMapper<SiteUsersDTO, SiteUsers> {
    @Mapping(target = "country", source = "country", qualifiedByName = "isoCountriesId")
    @Mapping(target = "feeSchedule", source = "feeSchedule", qualifiedByName = "feeScheduleId")
    @Mapping(target = "defaultCurrency", source = "defaultCurrency", qualifiedByName = "currenciesId")
    SiteUsersDTO toDto(SiteUsers s);

    @Named("isoCountriesId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    IsoCountriesDTO toDtoIsoCountriesId(IsoCountries isoCountries);

    @Named("feeScheduleId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    FeeScheduleDTO toDtoFeeScheduleId(FeeSchedule feeSchedule);

    @Named("currenciesId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    CurrenciesDTO toDtoCurrenciesId(Currencies currencies);
}
