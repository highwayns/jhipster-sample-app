package io.github.jhipster.sample.service.mapper;

import io.github.jhipster.sample.domain.AdminImageSizes;
import io.github.jhipster.sample.service.dto.AdminImageSizesDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link AdminImageSizes} and its DTO {@link AdminImageSizesDTO}.
 */
@Mapper(componentModel = "spring")
public interface AdminImageSizesMapper extends EntityMapper<AdminImageSizesDTO, AdminImageSizes> {}
