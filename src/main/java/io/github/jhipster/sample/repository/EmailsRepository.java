package io.github.jhipster.sample.repository;

import io.github.jhipster.sample.domain.Emails;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Emails entity.
 */
@SuppressWarnings("unused")
@Repository
public interface EmailsRepository extends JpaRepository<Emails, Long> {}
