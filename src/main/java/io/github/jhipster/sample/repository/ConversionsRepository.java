package io.github.jhipster.sample.repository;

import io.github.jhipster.sample.domain.Conversions;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Conversions entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ConversionsRepository extends JpaRepository<Conversions, Long> {}
