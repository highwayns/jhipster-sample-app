package io.github.jhipster.sample.repository;

import io.github.jhipster.sample.domain.RequestDescriptions;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the RequestDescriptions entity.
 */
@SuppressWarnings("unused")
@Repository
public interface RequestDescriptionsRepository extends JpaRepository<RequestDescriptions, Long> {}
