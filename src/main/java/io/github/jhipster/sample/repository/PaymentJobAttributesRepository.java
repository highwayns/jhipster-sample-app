package io.github.jhipster.sample.repository;

import io.github.jhipster.sample.domain.PaymentJobAttributes;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the PaymentJobAttributes entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PaymentJobAttributesRepository extends JpaRepository<PaymentJobAttributes, Long> {}
