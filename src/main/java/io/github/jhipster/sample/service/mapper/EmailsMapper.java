package io.github.jhipster.sample.service.mapper;

import io.github.jhipster.sample.domain.Emails;
import io.github.jhipster.sample.service.dto.EmailsDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Emails} and its DTO {@link EmailsDTO}.
 */
@Mapper(componentModel = "spring")
public interface EmailsMapper extends EntityMapper<EmailsDTO, Emails> {}
