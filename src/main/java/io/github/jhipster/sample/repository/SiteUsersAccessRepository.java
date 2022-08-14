package io.github.jhipster.sample.repository;

import io.github.jhipster.sample.domain.SiteUsersAccess;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the SiteUsersAccess entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SiteUsersAccessRepository extends JpaRepository<SiteUsersAccess, Long> {}
