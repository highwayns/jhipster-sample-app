package io.github.jhipster.sample.repository;

import io.github.jhipster.sample.domain.Currencys;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Currencys entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CurrencysRepository extends JpaRepository<Currencys, Long> {}
