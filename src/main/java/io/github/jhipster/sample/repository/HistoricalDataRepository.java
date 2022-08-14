package io.github.jhipster.sample.repository;

import io.github.jhipster.sample.domain.HistoricalData;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the HistoricalData entity.
 */
@SuppressWarnings("unused")
@Repository
public interface HistoricalDataRepository extends JpaRepository<HistoricalData, Long> {}
