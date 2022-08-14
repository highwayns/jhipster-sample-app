package io.github.jhipster.sample.repository;

import io.github.jhipster.sample.domain.AdminControls;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the AdminControls entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AdminControlsRepository extends JpaRepository<AdminControls, Long> {}
