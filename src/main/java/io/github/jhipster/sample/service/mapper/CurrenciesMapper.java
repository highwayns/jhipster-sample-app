package io.github.jhipster.sample.service.mapper;

import io.github.jhipster.sample.domain.Currencies;
import io.github.jhipster.sample.service.dto.CurrenciesDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Currencies} and its DTO {@link CurrenciesDTO}.
 */
@Mapper(componentModel = "spring")
public interface CurrenciesMapper extends EntityMapper<CurrenciesDTO, Currencies> {}
