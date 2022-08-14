package io.github.jhipster.sample.service.mapper;

import io.github.jhipster.sample.domain.Currencies;
import io.github.jhipster.sample.domain.Status;
import io.github.jhipster.sample.domain.StatusEscrows;
import io.github.jhipster.sample.service.dto.CurrenciesDTO;
import io.github.jhipster.sample.service.dto.StatusDTO;
import io.github.jhipster.sample.service.dto.StatusEscrowsDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link StatusEscrows} and its DTO {@link StatusEscrowsDTO}.
 */
@Mapper(componentModel = "spring")
public interface StatusEscrowsMapper extends EntityMapper<StatusEscrowsDTO, StatusEscrows> {
    @Mapping(target = "currency", source = "currency", qualifiedByName = "currenciesId")
    @Mapping(target = "statusId", source = "statusId", qualifiedByName = "statusId")
    StatusEscrowsDTO toDto(StatusEscrows s);

    @Named("currenciesId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    CurrenciesDTO toDtoCurrenciesId(Currencies currencies);

    @Named("statusId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    StatusDTO toDtoStatusId(Status status);
}
