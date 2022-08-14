package io.github.jhipster.sample.service.mapper;

import io.github.jhipster.sample.domain.SiteUsers;
import io.github.jhipster.sample.domain.SiteUsersCatch;
import io.github.jhipster.sample.service.dto.SiteUsersCatchDTO;
import io.github.jhipster.sample.service.dto.SiteUsersDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link SiteUsersCatch} and its DTO {@link SiteUsersCatchDTO}.
 */
@Mapper(componentModel = "spring")
public interface SiteUsersCatchMapper extends EntityMapper<SiteUsersCatchDTO, SiteUsersCatch> {
    @Mapping(target = "siteUser", source = "siteUser", qualifiedByName = "siteUsersId")
    SiteUsersCatchDTO toDto(SiteUsersCatch s);

    @Named("siteUsersId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    SiteUsersDTO toDtoSiteUsersId(SiteUsers siteUsers);
}
