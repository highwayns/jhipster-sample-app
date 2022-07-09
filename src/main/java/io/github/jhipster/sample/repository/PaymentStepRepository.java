package io.github.jhipster.sample.repository;

import io.github.jhipster.sample.domain.PaymentStep;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the PaymentStep entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PaymentStepRepository extends JpaRepository<PaymentStep, Long> {}
