package io.github.jhipster.sample.service.mapper;

import io.github.jhipster.sample.domain.ApiKeys;
import io.github.jhipster.sample.domain.SiteUsers;
import io.github.jhipster.sample.service.dto.ApiKeysDTO;
import io.github.jhipster.sample.service.dto.SiteUsersDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link ApiKeys} and its DTO {@link ApiKeysDTO}.
 */
@Mapper(componentModel = "spring")
public interface ApiKeysMapper extends EntityMapper<ApiKeysDTO, ApiKeys> {
    @Mapping(target = "siteUser", source = "siteUser", qualifiedByName = "siteUsersId")
    ApiKeysDTO toDto(ApiKeys s);

    @Named("siteUsersId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    SiteUsersDTO toDtoSiteUsersId(SiteUsers siteUsers);
}
