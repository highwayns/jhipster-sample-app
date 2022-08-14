package io.github.jhipster.sample.repository;

import io.github.jhipster.sample.domain.SiteUsers;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the SiteUsers entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SiteUsersRepository extends JpaRepository<SiteUsers, Long> {}
