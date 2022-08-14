package io.github.jhipster.sample.repository;

import io.github.jhipster.sample.domain.AdminGroupsTabs;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the AdminGroupsTabs entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AdminGroupsTabsRepository extends JpaRepository<AdminGroupsTabs, Long> {}
