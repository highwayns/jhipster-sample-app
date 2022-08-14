package io.github.jhipster.sample.repository;

import io.github.jhipster.sample.domain.AdminSessions;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the AdminSessions entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AdminSessionsRepository extends JpaRepository<AdminSessions, Long> {}
