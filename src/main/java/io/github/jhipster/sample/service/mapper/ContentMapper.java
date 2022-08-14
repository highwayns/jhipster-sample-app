package io.github.jhipster.sample.service.mapper;

import io.github.jhipster.sample.domain.Content;
import io.github.jhipster.sample.service.dto.ContentDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Content} and its DTO {@link ContentDTO}.
 */
@Mapper(componentModel = "spring")
public interface ContentMapper extends EntityMapper<ContentDTO, Content> {}
