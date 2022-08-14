package io.github.jhipster.sample.service.mapper;

import io.github.jhipster.sample.domain.AdminTabs;
import io.github.jhipster.sample.service.dto.AdminTabsDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link AdminTabs} and its DTO {@link AdminTabsDTO}.
 */
@Mapper(componentModel = "spring")
public interface AdminTabsMapper extends EntityMapper<AdminTabsDTO, AdminTabs> {}
