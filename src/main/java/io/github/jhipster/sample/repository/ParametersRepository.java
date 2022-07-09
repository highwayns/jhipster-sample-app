package io.github.jhipster.sample.repository;

import io.github.jhipster.sample.domain.Parameters;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Parameters entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ParametersRepository extends JpaRepository<Parameters, Long> {}
