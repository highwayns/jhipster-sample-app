package io.github.jhipster.sample.service.mapper;

import io.github.jhipster.sample.domain.AppConfiguration;
import io.github.jhipster.sample.service.dto.AppConfigurationDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link AppConfiguration} and its DTO {@link AppConfigurationDTO}.
 */
@Mapper(componentModel = "spring")
public interface AppConfigurationMapper extends EntityMapper<AppConfigurationDTO, AppConfiguration> {}
