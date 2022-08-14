package io.github.jhipster.sample.service.mapper;

import io.github.jhipster.sample.domain.TransactionTypes;
import io.github.jhipster.sample.service.dto.TransactionTypesDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link TransactionTypes} and its DTO {@link TransactionTypesDTO}.
 */
@Mapper(componentModel = "spring")
public interface TransactionTypesMapper extends EntityMapper<TransactionTypesDTO, TransactionTypes> {}
