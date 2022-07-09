package io.github.jhipster.sample.repository;

import io.github.jhipster.sample.domain.Links;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Links entity.
 */
@SuppressWarnings("unused")
@Repository
public interface LinksRepository extends JpaRepository<Links, Long> {}
