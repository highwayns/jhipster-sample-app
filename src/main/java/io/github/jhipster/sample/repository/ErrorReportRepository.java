package io.github.jhipster.sample.repository;

import io.github.jhipster.sample.domain.ErrorReport;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the ErrorReport entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ErrorReportRepository extends JpaRepository<ErrorReport, Long> {}
