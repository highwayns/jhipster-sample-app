package io.github.jhipster.sample.service.mapper;

import io.github.jhipster.sample.domain.AdminGroups;
import io.github.jhipster.sample.domain.AdminGroupsTabs;
import io.github.jhipster.sample.domain.AdminTabs;
import io.github.jhipster.sample.service.dto.AdminGroupsDTO;
import io.github.jhipster.sample.service.dto.AdminGroupsTabsDTO;
import io.github.jhipster.sample.service.dto.AdminTabsDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link AdminGroupsTabs} and its DTO {@link AdminGroupsTabsDTO}.
 */
@Mapper(componentModel = "spring")
public interface AdminGroupsTabsMapper extends EntityMapper<AdminGroupsTabsDTO, AdminGroupsTabs> {
    @Mapping(target = "tabId", source = "tabId", qualifiedByName = "adminTabsId")
    @Mapping(target = "groupId", source = "groupId", qualifiedByName = "adminGroupsId")
    AdminGroupsTabsDTO toDto(AdminGroupsTabs s);

    @Named("adminTabsId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    AdminTabsDTO toDtoAdminTabsId(AdminTabs adminTabs);

    @Named("adminGroupsId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    AdminGroupsDTO toDtoAdminGroupsId(AdminGroups adminGroups);
}
