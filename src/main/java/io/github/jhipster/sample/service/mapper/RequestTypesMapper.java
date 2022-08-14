package io.github.jhipster.sample.service.mapper;

import io.github.jhipster.sample.domain.RequestTypes;
import io.github.jhipster.sample.service.dto.RequestTypesDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link RequestTypes} and its DTO {@link RequestTypesDTO}.
 */
@Mapper(componentModel = "spring")
public interface RequestTypesMapper extends EntityMapper<RequestTypesDTO, RequestTypes> {}
