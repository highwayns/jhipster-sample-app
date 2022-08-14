package io.github.jhipster.sample.repository;

import io.github.jhipster.sample.domain.IsoCountries;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the IsoCountries entity.
 */
@SuppressWarnings("unused")
@Repository
public interface IsoCountriesRepository extends JpaRepository<IsoCountries, Long> {}
