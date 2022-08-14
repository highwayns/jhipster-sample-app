package io.github.jhipster.sample.repository;

import io.github.jhipster.sample.domain.CurrentStats;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the CurrentStats entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CurrentStatsRepository extends JpaRepository<CurrentStats, Long> {}
