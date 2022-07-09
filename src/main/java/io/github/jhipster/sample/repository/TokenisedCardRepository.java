package io.github.jhipster.sample.repository;

import io.github.jhipster.sample.domain.TokenisedCard;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the TokenisedCard entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TokenisedCardRepository extends JpaRepository<TokenisedCard, Long> {}
