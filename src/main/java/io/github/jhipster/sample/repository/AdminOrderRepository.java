package io.github.jhipster.sample.repository;

import io.github.jhipster.sample.domain.AdminOrder;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the AdminOrder entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AdminOrderRepository extends JpaRepository<AdminOrder, Long> {}
