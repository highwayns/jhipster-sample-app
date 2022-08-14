package io.github.jhipster.sample.repository;

import io.github.jhipster.sample.domain.IpAccessLog;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the IpAccessLog entity.
 */
@SuppressWarnings("unused")
@Repository
public interface IpAccessLogRepository extends JpaRepository<IpAccessLog, Long> {}
