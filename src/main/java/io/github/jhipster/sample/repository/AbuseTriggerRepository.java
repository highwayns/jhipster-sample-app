package io.github.jhipster.sample.repository;

import io.github.jhipster.sample.domain.AbuseTrigger;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the AbuseTrigger entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AbuseTriggerRepository extends JpaRepository<AbuseTrigger, Long> {}
