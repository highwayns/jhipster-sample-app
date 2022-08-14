package io.github.jhipster.sample.service.mapper;

import io.github.jhipster.sample.domain.HistoryActions;
import io.github.jhipster.sample.service.dto.HistoryActionsDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link HistoryActions} and its DTO {@link HistoryActionsDTO}.
 */
@Mapper(componentModel = "spring")
public interface HistoryActionsMapper extends EntityMapper<HistoryActionsDTO, HistoryActions> {}
