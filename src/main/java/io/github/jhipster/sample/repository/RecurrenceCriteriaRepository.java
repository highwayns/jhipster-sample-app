package io.github.jhipster.sample.repository;

import io.github.jhipster.sample.domain.RecurrenceCriteria;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the RecurrenceCriteria entity.
 */
@SuppressWarnings("unused")
@Repository
public interface RecurrenceCriteriaRepository extends JpaRepository<RecurrenceCriteria, Long> {}
