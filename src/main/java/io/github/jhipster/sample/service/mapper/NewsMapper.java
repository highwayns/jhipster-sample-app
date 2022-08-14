package io.github.jhipster.sample.service.mapper;

import io.github.jhipster.sample.domain.News;
import io.github.jhipster.sample.service.dto.NewsDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link News} and its DTO {@link NewsDTO}.
 */
@Mapper(componentModel = "spring")
public interface NewsMapper extends EntityMapper<NewsDTO, News> {}
