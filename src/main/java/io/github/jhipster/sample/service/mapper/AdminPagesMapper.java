package io.github.jhipster.sample.service.mapper;

import io.github.jhipster.sample.domain.AdminPages;
import io.github.jhipster.sample.service.dto.AdminPagesDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link AdminPages} and its DTO {@link AdminPagesDTO}.
 */
@Mapper(componentModel = "spring")
public interface AdminPagesMapper extends EntityMapper<AdminPagesDTO, AdminPages> {}
