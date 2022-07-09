package io.github.jhipster.sample.repository;

import io.github.jhipster.sample.domain.ResultAttributes;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the ResultAttributes entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ResultAttributesRepository extends JpaRepository<ResultAttributes, Long> {}
