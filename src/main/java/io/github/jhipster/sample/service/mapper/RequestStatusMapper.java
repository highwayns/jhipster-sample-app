package io.github.jhipster.sample.service.mapper;

import io.github.jhipster.sample.domain.RequestStatus;
import io.github.jhipster.sample.service.dto.RequestStatusDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link RequestStatus} and its DTO {@link RequestStatusDTO}.
 */
@Mapper(componentModel = "spring")
public interface RequestStatusMapper extends EntityMapper<RequestStatusDTO, RequestStatus> {}
