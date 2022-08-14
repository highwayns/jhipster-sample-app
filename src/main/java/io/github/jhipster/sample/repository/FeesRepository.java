package io.github.jhipster.sample.repository;

import io.github.jhipster.sample.domain.Fees;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Fees entity.
 */
@SuppressWarnings("unused")
@Repository
public interface FeesRepository extends JpaRepository<Fees, Long> {}
