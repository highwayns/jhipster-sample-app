package io.github.jhipster.sample.repository;

import io.github.jhipster.sample.domain.Issuer;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Issuer entity.
 */
@SuppressWarnings("unused")
@Repository
public interface IssuerRepository extends JpaRepository<Issuer, String> {}
