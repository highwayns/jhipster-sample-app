package io.github.jhipster.sample.repository;

import io.github.jhipster.sample.domain.AdminControlsMethods;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the AdminControlsMethods entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AdminControlsMethodsRepository extends JpaRepository<AdminControlsMethods, Long> {}
