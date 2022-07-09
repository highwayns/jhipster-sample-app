package io.github.jhipster.sample.repository;

import io.github.jhipster.sample.domain.AbuseReport;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the AbuseReport entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AbuseReportRepository extends JpaRepository<AbuseReport, Long> {}
