package io.github.jhipster.sample.repository;

import io.github.jhipster.sample.domain.SiteUsersBalances;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the SiteUsersBalances entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SiteUsersBalancesRepository extends JpaRepository<SiteUsersBalances, Long> {}
