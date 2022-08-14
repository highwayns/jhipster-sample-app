package io.github.jhipster.sample.service.mapper;

import io.github.jhipster.sample.domain.HistoricalData;
import io.github.jhipster.sample.service.dto.HistoricalDataDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link HistoricalData} and its DTO {@link HistoricalDataDTO}.
 */
@Mapper(componentModel = "spring")
public interface HistoricalDataMapper extends EntityMapper<HistoricalDataDTO, HistoricalData> {}
