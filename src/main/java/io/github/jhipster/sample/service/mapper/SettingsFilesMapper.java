package io.github.jhipster.sample.service.mapper;

import io.github.jhipster.sample.domain.SettingsFiles;
import io.github.jhipster.sample.service.dto.SettingsFilesDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link SettingsFiles} and its DTO {@link SettingsFilesDTO}.
 */
@Mapper(componentModel = "spring")
public interface SettingsFilesMapper extends EntityMapper<SettingsFilesDTO, SettingsFiles> {}
