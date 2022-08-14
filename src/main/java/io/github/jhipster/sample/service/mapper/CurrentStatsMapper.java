package io.github.jhipster.sample.service.mapper;

import io.github.jhipster.sample.domain.CurrentStats;
import io.github.jhipster.sample.service.dto.CurrentStatsDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link CurrentStats} and its DTO {@link CurrentStatsDTO}.
 */
@Mapper(componentModel = "spring")
public interface CurrentStatsMapper extends EntityMapper<CurrentStatsDTO, CurrentStats> {}
