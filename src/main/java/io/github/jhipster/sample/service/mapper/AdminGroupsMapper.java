package io.github.jhipster.sample.service.mapper;

import io.github.jhipster.sample.domain.AdminGroups;
import io.github.jhipster.sample.service.dto.AdminGroupsDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link AdminGroups} and its DTO {@link AdminGroupsDTO}.
 */
@Mapper(componentModel = "spring")
public interface AdminGroupsMapper extends EntityMapper<AdminGroupsDTO, AdminGroups> {}
