package io.github.jhipster.sample.repository;

import io.github.jhipster.sample.domain.CardTokenData;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the CardTokenData entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CardTokenDataRepository extends JpaRepository<CardTokenData, Long> {}
