package io.github.jhipster.sample.repository;

import io.github.jhipster.sample.domain.MonthlyReports;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the MonthlyReports entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MonthlyReportsRepository extends JpaRepository<MonthlyReports, Long> {}
