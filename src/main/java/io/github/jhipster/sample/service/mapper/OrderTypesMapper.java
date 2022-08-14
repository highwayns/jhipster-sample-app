package io.github.jhipster.sample.service.mapper;

import io.github.jhipster.sample.domain.OrderTypes;
import io.github.jhipster.sample.service.dto.OrderTypesDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link OrderTypes} and its DTO {@link OrderTypesDTO}.
 */
@Mapper(componentModel = "spring")
public interface OrderTypesMapper extends EntityMapper<OrderTypesDTO, OrderTypes> {}
