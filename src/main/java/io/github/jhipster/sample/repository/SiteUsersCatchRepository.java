package io.github.jhipster.sample.repository;

import io.github.jhipster.sample.domain.SiteUsersCatch;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the SiteUsersCatch entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SiteUsersCatchRepository extends JpaRepository<SiteUsersCatch, Long> {}
