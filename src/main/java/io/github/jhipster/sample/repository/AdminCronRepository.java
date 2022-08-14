package io.github.jhipster.sample.repository;

import io.github.jhipster.sample.domain.AdminCron;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the AdminCron entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AdminCronRepository extends JpaRepository<AdminCron, Long> {}
