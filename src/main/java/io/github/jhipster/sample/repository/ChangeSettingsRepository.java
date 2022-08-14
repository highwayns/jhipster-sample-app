package io.github.jhipster.sample.repository;

import io.github.jhipster.sample.domain.ChangeSettings;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the ChangeSettings entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ChangeSettingsRepository extends JpaRepository<ChangeSettings, Long> {}
