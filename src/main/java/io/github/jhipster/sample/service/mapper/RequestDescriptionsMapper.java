package io.github.jhipster.sample.service.mapper;

import io.github.jhipster.sample.domain.RequestDescriptions;
import io.github.jhipster.sample.service.dto.RequestDescriptionsDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link RequestDescriptions} and its DTO {@link RequestDescriptionsDTO}.
 */
@Mapper(componentModel = "spring")
public interface RequestDescriptionsMapper extends EntityMapper<RequestDescriptionsDTO, RequestDescriptions> {}
