package io.github.jhipster.sample.service.mapper;

import io.github.jhipster.sample.domain.IsoCountries;
import io.github.jhipster.sample.service.dto.IsoCountriesDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link IsoCountries} and its DTO {@link IsoCountriesDTO}.
 */
@Mapper(componentModel = "spring")
public interface IsoCountriesMapper extends EntityMapper<IsoCountriesDTO, IsoCountries> {}
