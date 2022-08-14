package io.github.jhipster.sample.service.mapper;

import io.github.jhipster.sample.domain.Status;
import io.github.jhipster.sample.service.dto.StatusDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Status} and its DTO {@link StatusDTO}.
 */
@Mapper(componentModel = "spring")
public interface StatusMapper extends EntityMapper<StatusDTO, Status> {}
