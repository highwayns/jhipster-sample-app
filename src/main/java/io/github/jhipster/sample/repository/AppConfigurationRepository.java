package io.github.jhipster.sample.repository;

import io.github.jhipster.sample.domain.AppConfiguration;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the AppConfiguration entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AppConfigurationRepository extends JpaRepository<AppConfiguration, Long> {}
