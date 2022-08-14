package io.github.jhipster.sample.service.mapper;

import io.github.jhipster.sample.domain.Conversions;
import io.github.jhipster.sample.domain.Currencies;
import io.github.jhipster.sample.service.dto.ConversionsDTO;
import io.github.jhipster.sample.service.dto.CurrenciesDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Conversions} and its DTO {@link ConversionsDTO}.
 */
@Mapper(componentModel = "spring")
public interface ConversionsMapper extends EntityMapper<ConversionsDTO, Conversions> {
    @Mapping(target = "currency", source = "currency", qualifiedByName = "currenciesId")
    ConversionsDTO toDto(Conversions s);

    @Named("currenciesId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    CurrenciesDTO toDtoCurrenciesId(Currencies currencies);
}
