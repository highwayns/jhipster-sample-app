package io.github.jhipster.sample.service.mapper;

import io.github.jhipster.sample.domain.AdminSessions;
import io.github.jhipster.sample.service.dto.AdminSessionsDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link AdminSessions} and its DTO {@link AdminSessionsDTO}.
 */
@Mapper(componentModel = "spring")
public interface AdminSessionsMapper extends EntityMapper<AdminSessionsDTO, AdminSessions> {}
