package io.github.jhipster.sample.service.mapper;

import io.github.jhipster.sample.domain.ChangeSettings;
import io.github.jhipster.sample.domain.SiteUsers;
import io.github.jhipster.sample.service.dto.ChangeSettingsDTO;
import io.github.jhipster.sample.service.dto.SiteUsersDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link ChangeSettings} and its DTO {@link ChangeSettingsDTO}.
 */
@Mapper(componentModel = "spring")
public interface ChangeSettingsMapper extends EntityMapper<ChangeSettingsDTO, ChangeSettings> {
    @Mapping(target = "siteUser", source = "siteUser", qualifiedByName = "siteUsersId")
    ChangeSettingsDTO toDto(ChangeSettings s);

    @Named("siteUsersId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    SiteUsersDTO toDtoSiteUsersId(SiteUsers siteUsers);
}
