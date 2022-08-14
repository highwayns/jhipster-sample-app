package io.github.jhipster.sample.service.mapper;

import io.github.jhipster.sample.domain.FeeSchedule;
import io.github.jhipster.sample.service.dto.FeeScheduleDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link FeeSchedule} and its DTO {@link FeeScheduleDTO}.
 */
@Mapper(componentModel = "spring")
public interface FeeScheduleMapper extends EntityMapper<FeeScheduleDTO, FeeSchedule> {}
