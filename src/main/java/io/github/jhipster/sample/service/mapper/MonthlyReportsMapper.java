package io.github.jhipster.sample.service.mapper;

import io.github.jhipster.sample.domain.MonthlyReports;
import io.github.jhipster.sample.service.dto.MonthlyReportsDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link MonthlyReports} and its DTO {@link MonthlyReportsDTO}.
 */
@Mapper(componentModel = "spring")
public interface MonthlyReportsMapper extends EntityMapper<MonthlyReportsDTO, MonthlyReports> {}
