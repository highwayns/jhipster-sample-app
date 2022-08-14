package io.github.jhipster.sample.service.mapper;

import io.github.jhipster.sample.domain.AdminControls;
import io.github.jhipster.sample.domain.AdminControlsMethods;
import io.github.jhipster.sample.domain.AdminCron;
import io.github.jhipster.sample.service.dto.AdminControlsDTO;
import io.github.jhipster.sample.service.dto.AdminControlsMethodsDTO;
import io.github.jhipster.sample.service.dto.AdminCronDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link AdminCron} and its DTO {@link AdminCronDTO}.
 */
@Mapper(componentModel = "spring")
public interface AdminCronMapper extends EntityMapper<AdminCronDTO, AdminCron> {
    @Mapping(target = "controlId", source = "controlId", qualifiedByName = "adminControlsId")
    @Mapping(target = "methodId", source = "methodId", qualifiedByName = "adminControlsMethodsId")
    AdminCronDTO toDto(AdminCron s);

    @Named("adminControlsId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    AdminControlsDTO toDtoAdminControlsId(AdminControls adminControls);

    @Named("adminControlsMethodsId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    AdminControlsMethodsDTO toDtoAdminControlsMethodsId(AdminControlsMethods adminControlsMethods);
}
