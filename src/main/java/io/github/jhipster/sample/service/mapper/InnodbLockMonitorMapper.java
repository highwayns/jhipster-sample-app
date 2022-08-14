package io.github.jhipster.sample.service.mapper;

import io.github.jhipster.sample.domain.InnodbLockMonitor;
import io.github.jhipster.sample.service.dto.InnodbLockMonitorDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link InnodbLockMonitor} and its DTO {@link InnodbLockMonitorDTO}.
 */
@Mapper(componentModel = "spring")
public interface InnodbLockMonitorMapper extends EntityMapper<InnodbLockMonitorDTO, InnodbLockMonitor> {}
