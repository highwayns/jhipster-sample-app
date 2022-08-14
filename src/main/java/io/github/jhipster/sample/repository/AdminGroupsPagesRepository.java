package io.github.jhipster.sample.repository;

import io.github.jhipster.sample.domain.AdminGroupsPages;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the AdminGroupsPages entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AdminGroupsPagesRepository extends JpaRepository<AdminGroupsPages, Long> {}
