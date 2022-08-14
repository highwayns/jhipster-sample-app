package io.github.jhipster.sample.service.mapper;

import io.github.jhipster.sample.domain.ContentFiles;
import io.github.jhipster.sample.service.dto.ContentFilesDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link ContentFiles} and its DTO {@link ContentFilesDTO}.
 */
@Mapper(componentModel = "spring")
public interface ContentFilesMapper extends EntityMapper<ContentFilesDTO, ContentFiles> {}
