package io.github.jhipster.sample.service.mapper;

import io.github.jhipster.sample.domain.Lang;
import io.github.jhipster.sample.service.dto.LangDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Lang} and its DTO {@link LangDTO}.
 */
@Mapper(componentModel = "spring")
public interface LangMapper extends EntityMapper<LangDTO, Lang> {}
