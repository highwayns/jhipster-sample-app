package io.github.jhipster.sample.repository;

import io.github.jhipster.sample.domain.Requests;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Requests entity.
 */
@SuppressWarnings("unused")
@Repository
public interface RequestsRepository extends JpaRepository<Requests, Long> {}
