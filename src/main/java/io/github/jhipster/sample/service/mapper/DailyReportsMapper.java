package io.github.jhipster.sample.service.mapper;

import io.github.jhipster.sample.domain.DailyReports;
import io.github.jhipster.sample.service.dto.DailyReportsDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link DailyReports} and its DTO {@link DailyReportsDTO}.
 */
@Mapper(componentModel = "spring")
public interface DailyReportsMapper extends EntityMapper<DailyReportsDTO, DailyReports> {}
