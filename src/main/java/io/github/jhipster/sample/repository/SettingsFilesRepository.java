package io.github.jhipster.sample.repository;

import io.github.jhipster.sample.domain.SettingsFiles;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the SettingsFiles entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SettingsFilesRepository extends JpaRepository<SettingsFiles, Long> {}
