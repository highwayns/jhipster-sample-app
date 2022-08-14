package io.github.jhipster.sample.service.mapper;

import io.github.jhipster.sample.domain.IpAccessLog;
import io.github.jhipster.sample.service.dto.IpAccessLogDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link IpAccessLog} and its DTO {@link IpAccessLogDTO}.
 */
@Mapper(componentModel = "spring")
public interface IpAccessLogMapper extends EntityMapper<IpAccessLogDTO, IpAccessLog> {}
