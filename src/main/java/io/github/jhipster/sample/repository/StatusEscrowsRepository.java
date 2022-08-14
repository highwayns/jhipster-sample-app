package io.github.jhipster.sample.repository;

import io.github.jhipster.sample.domain.StatusEscrows;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the StatusEscrows entity.
 */
@SuppressWarnings("unused")
@Repository
public interface StatusEscrowsRepository extends JpaRepository<StatusEscrows, Long> {}
