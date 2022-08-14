package io.github.jhipster.sample.service.mapper;

import io.github.jhipster.sample.domain.Fees;
import io.github.jhipster.sample.service.dto.FeesDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Fees} and its DTO {@link FeesDTO}.
 */
@Mapper(componentModel = "spring")
public interface FeesMapper extends EntityMapper<FeesDTO, Fees> {}
