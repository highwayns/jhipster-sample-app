package io.github.jhipster.sample.service.mapper;

import io.github.jhipster.sample.domain.AdminControls;
import io.github.jhipster.sample.domain.AdminOrder;
import io.github.jhipster.sample.domain.AdminUsers;
import io.github.jhipster.sample.service.dto.AdminControlsDTO;
import io.github.jhipster.sample.service.dto.AdminOrderDTO;
import io.github.jhipster.sample.service.dto.AdminUsersDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link AdminOrder} and its DTO {@link AdminOrderDTO}.
 */
@Mapper(componentModel = "spring")
public interface AdminOrderMapper extends EntityMapper<AdminOrderDTO, AdminOrder> {
    @Mapping(target = "controlId", source = "controlId", qualifiedByName = "adminControlsId")
    @Mapping(target = "userId", source = "userId", qualifiedByName = "adminUsersId")
    AdminOrderDTO toDto(AdminOrder s);

    @Named("adminControlsId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    AdminControlsDTO toDtoAdminControlsId(AdminControls adminControls);

    @Named("adminUsersId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    AdminUsersDTO toDtoAdminUsersId(AdminUsers adminUsers);
}
