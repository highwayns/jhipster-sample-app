package io.github.jhipster.sample.repository;

import io.github.jhipster.sample.domain.OrderTypes;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the OrderTypes entity.
 */
@SuppressWarnings("unused")
@Repository
public interface OrderTypesRepository extends JpaRepository<OrderTypes, Long> {}
