package io.github.jhipster.sample.repository;

import io.github.jhipster.sample.domain.BitcoinAddresses;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the BitcoinAddresses entity.
 */
@SuppressWarnings("unused")
@Repository
public interface BitcoinAddressesRepository extends JpaRepository<BitcoinAddresses, Long> {}
