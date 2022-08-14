package io.github.jhipster.sample.repository;

import io.github.jhipster.sample.domain.AdminTabs;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the AdminTabs entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AdminTabsRepository extends JpaRepository<AdminTabs, Long> {}
