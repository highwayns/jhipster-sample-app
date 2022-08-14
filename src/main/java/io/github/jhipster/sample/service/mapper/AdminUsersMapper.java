package io.github.jhipster.sample.service.mapper;

import io.github.jhipster.sample.domain.AdminUsers;
import io.github.jhipster.sample.domain.IsoCountries;
import io.github.jhipster.sample.service.dto.AdminUsersDTO;
import io.github.jhipster.sample.service.dto.IsoCountriesDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link AdminUsers} and its DTO {@link AdminUsersDTO}.
 */
@Mapper(componentModel = "spring")
public interface AdminUsersMapper extends EntityMapper<AdminUsersDTO, AdminUsers> {
    @Mapping(target = "countryId", source = "countryId", qualifiedByName = "isoCountriesId")
    AdminUsersDTO toDto(AdminUsers s);

    @Named("isoCountriesId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    IsoCountriesDTO toDtoIsoCountriesId(IsoCountries isoCountries);
}
