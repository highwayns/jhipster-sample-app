package io.github.jhipster.sample.repository;

import io.github.jhipster.sample.domain.OrderLog;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the OrderLog entity.
 */
@SuppressWarnings("unused")
@Repository
public interface OrderLogRepository extends JpaRepository<OrderLog, Long> {}
