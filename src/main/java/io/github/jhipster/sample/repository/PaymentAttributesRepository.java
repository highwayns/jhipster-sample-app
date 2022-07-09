package io.github.jhipster.sample.repository;

import io.github.jhipster.sample.domain.PaymentAttributes;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the PaymentAttributes entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PaymentAttributesRepository extends JpaRepository<PaymentAttributes, Long> {}
