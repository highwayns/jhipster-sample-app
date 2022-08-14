package io.github.jhipster.sample.repository;

import io.github.jhipster.sample.domain.AdminPages;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the AdminPages entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AdminPagesRepository extends JpaRepository<AdminPages, Long> {}
