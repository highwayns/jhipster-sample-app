package io.github.jhipster.sample.repository;

import io.github.jhipster.sample.domain.AdminGroups;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the AdminGroups entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AdminGroupsRepository extends JpaRepository<AdminGroups, Long> {}
