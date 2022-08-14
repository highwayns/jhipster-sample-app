package io.github.jhipster.sample.repository;

import io.github.jhipster.sample.domain.BitcoindLog;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the BitcoindLog entity.
 */
@SuppressWarnings("unused")
@Repository
public interface BitcoindLogRepository extends JpaRepository<BitcoindLog, Long> {}
