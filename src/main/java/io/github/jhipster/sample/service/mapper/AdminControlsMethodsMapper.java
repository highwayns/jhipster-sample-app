package io.github.jhipster.sample.service.mapper;

import io.github.jhipster.sample.domain.AdminControls;
import io.github.jhipster.sample.domain.AdminControlsMethods;
import io.github.jhipster.sample.service.dto.AdminControlsDTO;
import io.github.jhipster.sample.service.dto.AdminControlsMethodsDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link AdminControlsMethods} and its DTO {@link AdminControlsMethodsDTO}.
 */
@Mapper(componentModel = "spring")
public interface AdminControlsMethodsMapper extends EntityMapper<AdminControlsMethodsDTO, AdminControlsMethods> {
    @Mapping(target = "controlId", source = "controlId", qualifiedByName = "adminControlsId")
    AdminControlsMethodsDTO toDto(AdminControlsMethods s);

    @Named("adminControlsId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    AdminControlsDTO toDtoAdminControlsId(AdminControls adminControls);
}
