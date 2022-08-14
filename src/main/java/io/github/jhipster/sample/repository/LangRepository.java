package io.github.jhipster.sample.repository;

import io.github.jhipster.sample.domain.Lang;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Lang entity.
 */
@SuppressWarnings("unused")
@Repository
public interface LangRepository extends JpaRepository<Lang, Long> {}
