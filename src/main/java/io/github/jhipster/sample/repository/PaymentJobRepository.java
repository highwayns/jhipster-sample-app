package io.github.jhipster.sample.repository;

import io.github.jhipster.sample.domain.PaymentJob;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the PaymentJob entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PaymentJobRepository extends JpaRepository<PaymentJob, Long> {}
