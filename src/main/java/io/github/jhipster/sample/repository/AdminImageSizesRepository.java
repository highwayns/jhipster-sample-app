package io.github.jhipster.sample.repository;

import io.github.jhipster.sample.domain.AdminImageSizes;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the AdminImageSizes entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AdminImageSizesRepository extends JpaRepository<AdminImageSizes, Long> {}
