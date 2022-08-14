package io.github.jhipster.sample.repository;

import io.github.jhipster.sample.domain.InnodbLockMonitor;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the InnodbLockMonitor entity.
 */
@SuppressWarnings("unused")
@Repository
public interface InnodbLockMonitorRepository extends JpaRepository<InnodbLockMonitor, Long> {}
