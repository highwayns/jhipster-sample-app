package io.github.jhipster.sample.service.mapper;

import io.github.jhipster.sample.domain.AdminControls;
import io.github.jhipster.sample.domain.AdminPages;
import io.github.jhipster.sample.domain.AdminTabs;
import io.github.jhipster.sample.service.dto.AdminControlsDTO;
import io.github.jhipster.sample.service.dto.AdminPagesDTO;
import io.github.jhipster.sample.service.dto.AdminTabsDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link AdminControls} and its DTO {@link AdminControlsDTO}.
 */
@Mapper(componentModel = "spring")
public interface AdminControlsMapper extends EntityMapper<AdminControlsDTO, AdminControls> {
    @Mapping(target = "pageId", source = "pageId", qualifiedByName = "adminPagesId")
    @Mapping(target = "tabId", source = "tabId", qualifiedByName = "adminTabsId")
    AdminControlsDTO toDto(AdminControls s);

    @Named("adminPagesId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    AdminPagesDTO toDtoAdminPagesId(AdminPages adminPages);

    @Named("adminTabsId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    AdminTabsDTO toDtoAdminTabsId(AdminTabs adminTabs);
}
