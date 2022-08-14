package io.github.jhipster.sample.repository;

import io.github.jhipster.sample.domain.FeeSchedule;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the FeeSchedule entity.
 */
@SuppressWarnings("unused")
@Repository
public interface FeeScheduleRepository extends JpaRepository<FeeSchedule, Long> {}
