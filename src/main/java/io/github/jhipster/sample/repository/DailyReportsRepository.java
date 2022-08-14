package io.github.jhipster.sample.repository;

import io.github.jhipster.sample.domain.DailyReports;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the DailyReports entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DailyReportsRepository extends JpaRepository<DailyReports, Long> {}
