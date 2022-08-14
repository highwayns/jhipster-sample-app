package io.github.jhipster.sample.service.mapper;

import io.github.jhipster.sample.domain.AdminGroups;
import io.github.jhipster.sample.domain.AdminGroupsPages;
import io.github.jhipster.sample.domain.AdminPages;
import io.github.jhipster.sample.service.dto.AdminGroupsDTO;
import io.github.jhipster.sample.service.dto.AdminGroupsPagesDTO;
import io.github.jhipster.sample.service.dto.AdminPagesDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link AdminGroupsPages} and its DTO {@link AdminGroupsPagesDTO}.
 */
@Mapper(componentModel = "spring")
public interface AdminGroupsPagesMapper extends EntityMapper<AdminGroupsPagesDTO, AdminGroupsPages> {
    @Mapping(target = "pageId", source = "pageId", qualifiedByName = "adminPagesId")
    @Mapping(target = "groupId", source = "groupId", qualifiedByName = "adminGroupsId")
    AdminGroupsPagesDTO toDto(AdminGroupsPages s);

    @Named("adminPagesId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    AdminPagesDTO toDtoAdminPagesId(AdminPages adminPages);

    @Named("adminGroupsId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    AdminGroupsDTO toDtoAdminGroupsId(AdminGroups adminGroups);
}
