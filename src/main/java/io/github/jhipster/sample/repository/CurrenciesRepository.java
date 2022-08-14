package io.github.jhipster.sample.repository;

import io.github.jhipster.sample.domain.Currencies;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Currencies entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CurrenciesRepository extends JpaRepository<Currencies, Long> {}
