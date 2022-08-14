package io.github.jhipster.sample.repository;

import io.github.jhipster.sample.domain.HistoryActions;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the HistoryActions entity.
 */
@SuppressWarnings("unused")
@Repository
public interface HistoryActionsRepository extends JpaRepository<HistoryActions, Long> {}
