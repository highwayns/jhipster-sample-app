package io.github.jhipster.sample.repository;

import io.github.jhipster.sample.domain.ApiKeys;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the ApiKeys entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ApiKeysRepository extends JpaRepository<ApiKeys, Long> {}
