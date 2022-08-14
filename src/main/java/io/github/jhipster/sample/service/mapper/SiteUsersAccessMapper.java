package io.github.jhipster.sample.service.mapper;

import io.github.jhipster.sample.domain.SiteUsers;
import io.github.jhipster.sample.domain.SiteUsersAccess;
import io.github.jhipster.sample.service.dto.SiteUsersAccessDTO;
import io.github.jhipster.sample.service.dto.SiteUsersDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link SiteUsersAccess} and its DTO {@link SiteUsersAccessDTO}.
 */
@Mapper(componentModel = "spring")
public interface SiteUsersAccessMapper extends EntityMapper<SiteUsersAccessDTO, SiteUsersAccess> {
    @Mapping(target = "siteUser", source = "siteUser", qualifiedByName = "siteUsersId")
    SiteUsersAccessDTO toDto(SiteUsersAccess s);

    @Named("siteUsersId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    SiteUsersDTO toDtoSiteUsersId(SiteUsers siteUsers);
}
