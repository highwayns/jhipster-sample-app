package io.github.jhipster.sample.service.mapper;

import io.github.jhipster.sample.domain.BitcoinAddresses;
import io.github.jhipster.sample.domain.SiteUsers;
import io.github.jhipster.sample.service.dto.BitcoinAddressesDTO;
import io.github.jhipster.sample.service.dto.SiteUsersDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link BitcoinAddresses} and its DTO {@link BitcoinAddressesDTO}.
 */
@Mapper(componentModel = "spring")
public interface BitcoinAddressesMapper extends EntityMapper<BitcoinAddressesDTO, BitcoinAddresses> {
    @Mapping(target = "siteUser", source = "siteUser", qualifiedByName = "siteUsersId")
    BitcoinAddressesDTO toDto(BitcoinAddresses s);

    @Named("siteUsersId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    SiteUsersDTO toDtoSiteUsersId(SiteUsers siteUsers);
}
