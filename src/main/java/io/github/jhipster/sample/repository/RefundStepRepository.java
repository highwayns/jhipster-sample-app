package io.github.jhipster.sample.repository;

import io.github.jhipster.sample.domain.RefundStep;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the RefundStep entity.
 */
@SuppressWarnings("unused")
@Repository
public interface RefundStepRepository extends JpaRepository<RefundStep, Long> {}
