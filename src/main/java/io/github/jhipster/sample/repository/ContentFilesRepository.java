package io.github.jhipster.sample.repository;

import io.github.jhipster.sample.domain.ContentFiles;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the ContentFiles entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ContentFilesRepository extends JpaRepository<ContentFiles, Long> {}
