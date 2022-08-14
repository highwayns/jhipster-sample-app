package io.github.jhipster.sample.service.mapper;

import io.github.jhipster.sample.domain.BitcoindLog;
import io.github.jhipster.sample.domain.SiteUsers;
import io.github.jhipster.sample.service.dto.BitcoindLogDTO;
import io.github.jhipster.sample.service.dto.SiteUsersDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link BitcoindLog} and its DTO {@link BitcoindLogDTO}.
 */
@Mapper(componentModel = "spring")
public interface BitcoindLogMapper extends EntityMapper<BitcoindLogDTO, BitcoindLog> {
    @Mapping(target = "siteUser", source = "siteUser", qualifiedByName = "siteUsersId")
    BitcoindLogDTO toDto(BitcoindLog s);

    @Named("siteUsersId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    SiteUsersDTO toDtoSiteUsersId(SiteUsers siteUsers);
}
